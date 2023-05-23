import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        //fazer conexção com o HTTP  dos Top 10 filmes da imDb
        String url = "https://imdb-api.com/en/API/Top250Movies/k_05uk3251";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        //extrair só os dados que interessa (título, pôster e classificação)
        var parser = new JsonPaser(); 
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        //extrair e manipular os dados como quiser
        var geradora = new GenereteSticker();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "saida/" + titulo + ".png";

            geradora.criar(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println();
            
           
        }
    }
}
