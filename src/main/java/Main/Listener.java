package Main;

import com.github.pwrlabs.pwrj.Block.Block;
import com.github.pwrlabs.pwrj.Transaction.Transaction;
import com.github.pwrlabs.pwrj.Transaction.VmDataTxn;
import com.github.pwrlabs.pwrj.protocol.PWRJ;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

//The listener continuously checks for new blocks and prints the messages sent to the VM
public class Listener {

    public static void listen() {
        new Thread() {
            public void run() {
                long latestBlockNumberChecked = 0;
                try {
                    latestBlockNumberChecked = PWRJ.getLatestBlockNumber();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while(true) {
                    try {
                        long currentBlockNumber = PWRJ.getLatestBlockNumber();

                        if (currentBlockNumber > latestBlockNumberChecked) {
                            Block block = PWRJ.getBlockByNumber(++latestBlockNumberChecked);

                            for (Transaction txn : block.getTransactions()) {
                                if (txn instanceof VmDataTxn) {
                                    VmDataTxn vmDataTxn = (VmDataTxn) txn;
                                    if (vmDataTxn.getVmId() == Main.vmId) {
                                        System.out.println(vmDataTxn.getFrom() + ": " + new String(Hex.decode(vmDataTxn.getData()), "UTF-8"));
                                        System.out.print("> ");
                                    }
                                }
                            }
                        }

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
