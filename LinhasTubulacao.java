package TrabalhoA3JAVA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinhasTubulacao {

    public static void main(String[] args) throws IOException {

        Scanner entrada = new Scanner(System.in);

        
        System.out.println("Digite o caminho da pasta com os arquivos .TXT: ");
        String caminhoPasta = entrada.nextLine();

        String padraoRegex = "\\b[0-9]{4}KS-[A-Z]{3}-[0-9.\\/]+\"-[A-Z0-9]{3,5}-[0-9]{3,4}" + "(?:\\s*\\(EXISTENTE\\))?\\b";

        Pattern pattern = Pattern.compile(padraoRegex, Pattern.CASE_INSENSITIVE);

        File pasta = new File(caminhoPasta);
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".txt"));

        String[] vetorTubulacao = new String[1000];
        String[] vetorArquivo = new String[1000];
        int totalEncontrado = 0;

        for (File arquivo : arquivos) {
            String conteudo = new String(Files.readAllBytes(arquivo.toPath()));

            Matcher matcher = pattern.matcher(conteudo);

            while (matcher.find()) {
                vetorTubulacao[totalEncontrado] = matcher.group();
                vetorArquivo[totalEncontrado] = arquivo.getName();
                totalEncontrado++;

            }
        }

        System.out.print("\nDigite o caminho para salvar o resultado: ");
        String caminhoSalvar = entrada.nextLine();

        String caminhoArquivoSaida = caminhoSalvar + File.separator + "resultado_tubulacoes.csv";

        FileWriter escritor = new FileWriter(caminhoArquivoSaida);

        escritor.write("Linha de tubulacao;Nome do Arquivo\n");
        

        for (int i = 0; i < totalEncontrado; i++) {
            escritor.write(vetorTubulacao[i] + ";" + vetorArquivo[i] + "\n");

        }

        escritor.close();

        System.out.println("Arquivo salvo em: " + caminhoArquivoSaida);
        System.out.println("Total de linhas encontradas: " + totalEncontrado);
        entrada.close();
        
    }
    
}
