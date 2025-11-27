import java.time.LocalTime;
import java.util.Random;

public class Produtor implements Runnable {

    int id;
    Buffer buffer;
    Config cfg;

    public Produtor(int id, Buffer buffer, Config cfg){
        this.id = id;
        this.buffer = buffer;
        this.cfg = cfg;
    }

    public void run(){
        Random r = new Random();

        for(int i=1; i<=cfg.itensPorProdutor; i++){
            try {
                long inicioEspera = System.nanoTime(); // mede bloqueio
                buffer.empty.acquire();
                buffer.mutex.acquire();
                long fimEspera = System.nanoTime();
                buffer.esperaProdutor[id] += (fimEspera - inicioEspera);

                buffer.fila.add(i);
                buffer.addProd(id);

                System.out.println(LocalTime.now() + " [Produtor " + id + "] produziu -> " + i);

                buffer.mutex.release();
                buffer.full.release();

                Thread.sleep(cfg.prodMin + r.nextInt(cfg.prodMax - cfg.prodMin + 1));

            }catch(Exception ignored){}
        }
    }
}
