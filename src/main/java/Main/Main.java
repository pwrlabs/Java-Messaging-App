package Main;

import com.github.pwrlabs.pwrj.Utils.Response;
import com.github.pwrlabs.pwrj.protocol.PWRJ;
import com.github.pwrlabs.pwrj.wallet.PWRWallet;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static final long vmId = 99;
    public static void main(String[] args) {
        PWRJ.setRpcNodeUrl("https://pwrrpc.pwrlabs.io/");

        PWRWallet wallet = new PWRWallet();

        System.out.println("Address: " + wallet.getAddress());

        Listener.listen();

        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        while(true) {
            System.out.println("> ");
            String message = myScanner.nextLine();  // Read user input
            try {
                Response r = wallet.sendVmDataTxn(vmId, message.getBytes(StandardCharsets.UTF_8));
                if(!r.isSuccess()) System.out.println("\nError: " + r.getError());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}