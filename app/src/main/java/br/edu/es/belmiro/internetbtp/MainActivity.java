package br.edu.es.belmiro.internetbtp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Baseado no mecanismo do internet-btp-quest, ja utilizado no Quest 3S.
 * Abre uma URL configurada no momento da compilacao no navegador nativo Meta.
 * Nao embute navegador, nao coleta dados, nao modifica o sistema do Quest.
 */
public class MainActivity extends Activity {
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createScreen();
        getWindow().getDecorView().post(this::openQuestBrowser);
    }

    private void createScreen() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER);
        box.setPadding(40, 28, 40, 28);
        box.setBackgroundColor(Color.rgb(14, 29, 65));

        TextView title = new TextView(this);
        title.setText(getString(R.string.app_name));
        title.setTextSize(34);
        title.setTextColor(Color.WHITE);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        box.addView(title);

        TextView info = new TextView(this);
        info.setText("Abrindo a pagina no navegador do Meta Quest...");
        info.setTextSize(19);
        info.setTextColor(Color.rgb(189, 231, 241));
        info.setGravity(Gravity.CENTER);
        info.setPadding(0, 22, 0, 16);
        box.addView(info);

        Button retry = new Button(this);
        retry.setText("ABRIR PAGINA");
        retry.setOnClickListener(v -> openQuestBrowser());
        box.addView(retry);

        status = new TextView(this);
        status.setText("Se a tela nao mudar, pressione ABRIR PAGINA.");
        status.setTextSize(15);
        status.setTextColor(Color.LTGRAY);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 22, 0, 0);
        box.addView(status);
        setContentView(box);
    }

    private void openQuestBrowser() {
        try {
            // Unico valor que muda entre APKs: a URL em strings.xml, gerada pelo GitHub.
            String targetUrl = getString(R.string.site_url);
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage("com.oculus.vrshell");
            if (intent == null) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setClassName("com.oculus.vrshell", "com.oculus.vrshell.MainActivity");
                intent.setData(android.net.Uri.parse("systemux://browser"));
            }
            intent.putExtra("intent_data", "systemux://browser");
            intent.putExtra("uri", targetUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            status.setText("Solicitacao enviada ao navegador Meta.");
        } catch (Exception ex) {
            status.setText("Nao foi possivel abrir a pagina: " + ex.getClass().getSimpleName());
        }
    }
}
