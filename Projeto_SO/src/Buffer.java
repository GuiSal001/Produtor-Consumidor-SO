import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Buffer {

    Queue<Integer> fila = new LinkedList<>();
    Semaphore mutex, empty, full;

    boolean producaoFinalizada = false;

    int[] prodCount = new int[10], consCount = new int[10];
    long[] esperaProdutor = new long[10], esperaConsumidor = new long[10];

    int totalP=0, totalC=0;

    public Buffer(int cap){
        mutex = new Semaphore(1);
        empty = new Semaphore(cap);
        full  = new Semaphore(0);
    }

    synchronized void addProd(int id){ prodCount[id]++; totalP++; }
    synchronized void addCons(int id){ consCount[id]++; totalC++; }

    void resumo(long tempo){

        System.out.println("\n=========== RESUMO FINAL ===========");
        System.out.println("Total produzido: " + totalP);
        System.out.println("Total consumido: " + totalC);
        System.out.println("Tempo total execução: " + tempo + " ms\n");

        System.out.println("Tempo médio de espera (produtores):");
        for(int i=1;i<prodCount.length;i++)
            if(prodCount[i]>0)
                System.out.println(" Produtor " + i + ": " + (esperaProdutor[i]/1_000_000) + " ms");

        System.out.println("\nTempo médio de espera (consumidores):");
        for(int i=1;i<consCount.length;i++)
            if(consCount[i]>0)
                System.out.println(" Consumidor " + i + ": " + (esperaConsumidor[i]/1_000_000) + " ms");

        System.out.println("\nInanição detectada? " + (verificarInanicao() ? "⚠ SIM" : "✔ NÃO"));
        System.out.println("====================================\n");
    }

    boolean verificarInanicao(){
        for(int i=1;i<consCount.length;i++)
            if(consCount[i]==0) return true;
        return false;
    }
}
