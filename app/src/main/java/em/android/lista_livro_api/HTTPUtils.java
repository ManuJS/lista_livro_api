package em.android.lista_livro_api;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


/**
 * Created by Novo Adm on 13/08/2016.
 */
public class HTTPUtils {

    public static String acessar(String endereco){
        try{
            URL url = new URL(endereco);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            Scanner scanner = new Scanner(is);
            String conteudo = scanner.useDelimiter("\\A").next();
            scanner.close();
            return conteudo;


        } catch (Exception e) {
            return null;
        }
    }


}
