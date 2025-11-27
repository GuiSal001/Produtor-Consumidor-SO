import java.time.LocalTime;
import java.util.Random;

public class Consumidor implements Runnable {

    int id;
    Buffer buffer;
    Config cfg;

    public Consumidor(int id, Buffer buffer, Config cfg){
        this.id=id;
        this.buffer=buffer;
        this.cfg=cfg;
    }

    public void run(){
        Random r = new Random();

        while(!buffer.producaoFinalizada || !buffer.fila.isEmpty()){
            try{
                long inicioEspera = System.nanoTime();
                buffer.full.acquire();
                buffer.mutex.acquire();
                long fimEspera = System.nanoTime();
                buffer.esperaConsumidor[id] += (fimEspera - inicioEspera);

                int item = buffer.fila.poll();
                buffer.addCons(id);

                System.out.println(LocalTime.now() + " [Consumidor " + id + "] consumiu -> " + item);

                buffer.mutex.release();
                buffer.empty.release();

                Thread.sleep(cfg.consMin + r.nextInt(cfg.consMax - cfg.consMin + 1));

            }catch(Exception ignored){}
        }
    }
}
