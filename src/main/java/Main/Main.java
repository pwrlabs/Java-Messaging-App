package Main;

import com.github.pwrlabs.pwrj.Utils.Response;
import com.github.pwrlabs.pwrj.protocol.PWRJ;
import com.github.pwrlabs.pwrj.wallet.PWRWallet;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static final long appId = 4567;

    public static void main(String[] args) {
        PWRJ.setRpcNodeUrl("https://pwrrpc.pwrlabs.io/");

        PWRWallet wallet = new PWRWallet();
        System.out.println("Address: " + wallet.getAddress());

        Listener.listen();

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            String message = scanner.nextLine();

            try {
                Response r = wallet.sendVmDataTxn(appId, message.getBytes(StandardCharsets.UTF_8));
                if(r.isSuccess()) {
                    System.out.println("Txn Hash: " + r.getMessage());
                } else {
                    System.out.println("Error: " + r.getError());
                }
            } catch (Exception e) { e.printStackTrace(); }

            System.out.print("> ");
        }
    }
}