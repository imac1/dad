package eu.ase.threads;

class Producer extends Thread {
    private int idP;//numar identificare producator
    private MarketProduct market;//piata in care se desfasoara tranzactiile
    
    public Producer(int idProducer, MarketProduct market) {
        this.idP = idProducer;
        this.market = market;
    }
    
    public void run() {
        for (int i = 1; i < 10; i++) {
              market.sell(i); //se vinde pe piata
              System.out.println("Producer: " + this.idP + " sells in the market (from product x):"+i);    
            try {this.sleep((int)(Math.random()*100));} catch(InterruptedException ie) {ie.printStackTrace();}
        }
    }
}

class Consumer extends Thread {
    private int idC;//numar identificare consumator
    private MarketProduct market;
    
    public Consumer(int idConsumer, MarketProduct market) {
        this.idC = idConsumer;
        this.market = market;
    }
    
    public void run() {
        int val = 0;
        for (int i = 1; i < 10; i++) {
              val = market.buy(); //se cumpara pe piata
              System.out.println("Consumer: " + this.idC + " buys from the market (from product x):"+val);
            try {this.sleep((int)(Math.random()*100));} catch(InterruptedException ie) {ie.printStackTrace();}
        }
    }
}

class MarketProduct {
    private volatile int quantity;
    private volatile boolean available = false;

    public synchronized int buy() {
        //if (!this.available) {
        while (!this.available) {
            try {this.wait();} catch(InterruptedException ie) {ie.printStackTrace();}
        }
        
        this.available = false;
        this.notify();
        return this.quantity;
    }
    
    public synchronized void sell(int quantity) {
        //if (available) {
        while (available) {
            try {this.wait();} catch(InterruptedException ie) {ie.printStackTrace();}
        }
        
        this.quantity = quantity;
        this.available = true;
        this.notify();
    }
}

class ProducerConsumer {
    public static void main(String[] args) {
        MarketProduct m = new MarketProduct();
        Producer p1 = new Producer(1, m);
        Consumer c1 = new Consumer(1, m);
        //Producer p2 = new Producer(2, m);
        //Consumer c2 = new Consumer(2, m);
        
        p1.start();
        c1.start();
        
        //p2.start();
        //c2.start();//acum apare regiune critica pe buy() si sell()
        //pt a functiona corect este normal ca in obiectul comun sa se puna
        //boolean available[] - vector (un element din vector este - (NU ESTE "semafor")-
	//vector de variabile mutex de sincronizare)
        //sau bucla while pt. ca notifyAll() nu se stie ce fir "trezeste"
        
        //asteapta sa se termine firul obiectului apelator c1
        //try {c1.join();} catch(InterruptedException ie) {ie.printStackTrace();}
        
        System.out.println("Main READY!");
    }
}
