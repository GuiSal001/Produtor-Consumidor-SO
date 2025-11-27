import java.net.InetAddress;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws Exception {

        Config cfg = Config.carregar("input.txt");

        System.out.println("Executando em: " + InetAddress.getLocalHost().getHostName());
        System.out.println("Início: " + LocalTime.now());
        System.out.println("--------------------------------------------------");

        Buffer buffer = new Buffer(cfg.buffer);

        Thread[] prod = new Thread[cfg.produtores];
        Thread[] cons = new Thread[cfg.consumidores];

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < cfg.produtores; i++) {
            prod[i] = new Thread(new Produtor(i+1, buffer, cfg));
            prod[i].start();
        }
        for (int i = 0; i < cfg.consumidores; i++) {
            cons[i] = new Thread(new Consumidor(i+1, buffer, cfg));
            cons[i].start();
        }

        for (Thread t : prod) t.join();
        buffer.producaoFinalizada = true;
        for (Thread t : cons) t.join();

        long fim = System.currentTimeMillis();

        buffer.resumo(fim - inicio);
    }
}
