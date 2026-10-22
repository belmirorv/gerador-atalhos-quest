# Gerador de Atalhos para Meta Quest 3S (BTP)

Este projeto preserva o mecanismo **ja utilizado** em `belmirorv/internet-btp-quest`:
abre o navegador nativo do Meta Quest pelo `systemux://browser` e envia uma URL
no campo `uri`. **Nao e um navegador embutido.**

## Ideia

Com **um so repositorio GitHub**, crie APKs de atalho diferentes, sem editar
`MainActivity.java`. Cada APK abre uma pagina da internet na interface do Quest:

| Nome | URL | Identificador |
| --- | --- | --- |
| INTERNET - BTP | https://www.google.com/ | internet |
| SIMULACOES - BTP | https://phet.colorado.edu/pt_BR/ | phet |
| WIKIPEDIA - BTP | https://pt.wikipedia.org/ | wikipedia |

**Cada identificador cria um aplicativo independente.** Se criar `phet` e
`wikipedia`, ambos poderao ficar instalados no mesmo aparelho.

## Passo a passo no GitHub, sem Android Studio

1. Crie um **novo repositorio** (recomendacao: `atalhos-btp-quest`). Nao apague
   nem sobrescreva o `internet-btp-quest`, que ja funciona.
2. Extraia este ZIP no computador. No novo repositorio clique **Add file >
   Upload files**. Envie os arquivos e pastas extraidos para a **raiz** do
   repositorio, incluindo `.github` (pasta oculta em alguns sistemas).
3. Clique **Commit changes**. Na aba **Actions** autorize os workflows, se o
   GitHub solicitar.
4. Abra **Actions > Gerar atalho Quest APK > Run workflow**.
5. Preencha:
   - `nome`: nome visivel do aplicativo, ex.: `SIMULACOES - BTP`
   - `url`: HTTPS completo, ex.: `https://phet.colorado.edu/pt_BR/`
   - `identificador`: uma palavra unica em minusculas, ex.: `phet`
6. Clique **Run workflow**. Quando a execucao ficar verde, abra-a, na secao
   **Artifacts** baixe `atalho-quest-apk-...` e extraia `app-debug.apk`.
7. Renomeie a copia baixada no Windows como `phet.apk` se desejar facilitar
   a organizacao. O nome do arquivo nao altera o identificador Android.
8. Teste em **um** Quest 3S antes de distribuir a todos pelo Meta Admin Center.
9. Para outra pagina, repita os passos 4-8 com outro `nome`, outra `url`
   e outro `identificador`. Nao e preciso editar o Java.

## Trocar uma URL existente

Para reconstruir o mesmo atalho com uma nova URL, use o **mesmo identificador**.
Entretanto, o workflow atual gera APKs **debug**; a assinatura de teste gerada
pelos servidores do GitHub pode mudar entre execucoes, impedindo atualizar o
APK ja instalado. Nesse caso, pode ser necessario remover o app anterior
antes de instalar o novo. Para a implantacao permanente de 30 aparelhos, configure
assinatura **release** com chave persistente, guardada em GitHub Secrets.
Nao coloque chaves e senhas de assinatura no codigo do repositorio.

## Icones e limitacoes

- Todos os APKs desse modelo usam o **mesmo icone generico**, mas nomes e URLs
  distintos. Para icones especificos, e preciso adicionar outra etapa ao build.
- Nao exige login no Google. Sites com autenticacao propria ainda podem pedi-la.
- Sites 2D comuns abrem no navegador nativo do Quest. Sites com recursos
  WebXR podem ter requisitos proprios e alguns podem ser bloqueados pela rede.
- O comportamento real deve ser testado no modo compartilhado ja utilizado.
- O projeto foi **preparado**, mas nao foi compilado nem testado em um Quest
  neste ambiente; validar o novo fluxo de build no GitHub.
- Nao use URL com dados pessoais ou tokens privados: entradas de build
  nao sao mecanismo seguro de guardar segredos.

**Fontes**:
- Meta: https://developers.meta.com/vr/documentation/native/ps-system-deep-linking/
- GitHub: https://docs.github.com/en/actions/how-tos/manage-workflow-runs/manually-run-a-workflow
