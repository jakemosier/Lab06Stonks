public class StonkAppMain {

    StonkMarket sm = new StonkMarket();
    StonksAppView view = new StonksAppView();
    Wallet w;

    public void go(){
        view.startScreen(); // call the startscreen object, brings up the start screen 
        walletInitializer(); // 
        view.printMenu();
        view.walletInfo(w, sm);
        String action = view.getAction("What would you like to do? ");
        while(!action.startsWith("x")) {
            parseAction(action);
            view.walletInfo(w, sm);
            action = view.getAction("What would you like to do? ");
        }
    }

    public void walletInitializer(){
        String walletChoice = view.getAction("Would you like to start with the default wallet, or enter your own?\n" +
                "Enter [d]efault or [c]ustom: ");
        if(walletChoice.startsWith("d")){
            w = new Wallet();
        }
        else{
            int fazCoin = Integer.parseInt(view.getAction("So you want to invest more!\n"
                    + "How much FazCoin do you already own? "));
            double usd = Double.parseDouble(view.getAction("How much money in USD do you plan to work with? "));
            w = new Wallet(fazCoin, usd);
        }
    }

    public void parseAction(String action) {
        if (action.startsWith("b")) {
            int num = Integer.parseInt(view.getAction("How much FazCoin would you like to buy? "));
            purchaseFazCoin(num);
        } else if (action.startsWith("s")) {
            int num = Integer.parseInt(view.getAction("How much FazCoin would you like to sell? "));
            sellFazCoin(num);
        } else if (action.startsWith("n") || action.startsWith("d")) {
            //Don't do anything
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    public void purchaseFazCoin(int numFazCoin) {
        if(sm.fazCoinToUSD(numFazCoin) <= w.getUSDollars()) {
            w.setFazCoin(numFazCoin + (w.getFazCoin()));
            w.setUSDollars(w.getUSDollars() - sm.fazCoinToUSD(numFazCoin));
        } else {
            System.out.println("You do not have enough money to buy that much FazCoin.");
            System.out.println("");
        }
    }

    public void sellFazCoin(int numFazCoin) {
        System.out.println("AMOUNT OF FAZ COIN " + w.getFazCoin());
        if(numFazCoin <= w.getFazCoin()) {
            w.setUSDollars(sm.fazCoinToUSD(numFazCoin) + (w.getUSDollars()));
            w.setFazCoin(w.getFazCoin() - numFazCoin);
        } else {
            System.out.println("You do not have enough FazCoin to sell.");
        }
    }

    public static void main(String[] args) {
        StonkAppMain m = new StonkAppMain();
        m.go();
    }

}
