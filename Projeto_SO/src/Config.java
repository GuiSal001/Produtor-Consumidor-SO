import java.io.BufferedReader;
import java.io.FileReader;

public class Config {

    public int produtores;
    public int consumidores;
    public int buffer;
    public int itensPorProdutor;

    public int prodMin, prodMax;
    public int consMin, consMax;

    public static Config carregar(String file) throws Exception {
        Config c = new Config();
        BufferedReader br = new BufferedReader(new FileReader(file));

        c.produtores        = Integer.parseInt(br.readLine().split("=")[1]);
        c.consumidores      = Integer.parseInt(br.readLine().split("=")[1]);
        c.buffer            = Integer.parseInt(br.readLine().split("=")[1]);
        c.itensPorProdutor  = Integer.parseInt(br.readLine().split("=")[1]);

        String[] p = br.readLine().split("=")[1].split("-");
        c.prodMin = Integer.parseInt(p[0]);
        c.prodMax = Integer.parseInt(p[1]);

        String[] s = br.readLine().split("=")[1].split("-");
        c.consMin = Integer.parseInt(s[0]);
        c.consMax = Integer.parseInt(s[1]);

        br.close();
        return c;
    }
}
