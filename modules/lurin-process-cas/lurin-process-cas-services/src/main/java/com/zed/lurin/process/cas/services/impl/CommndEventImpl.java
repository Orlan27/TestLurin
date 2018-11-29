package com.zed.lurin.process.cas.services.impl;

import com.zed.lurin.commons.utils.Validations;
import com.zed.lurin.process.cas.services.ICommndEvent;
import com.zed.lurin.process.cas.command.CommandCas;
import com.zed.lurin.process.cas.dto.CampaingCAS;
import com.zed.lurin.process.cas.dto.Costumers;
import com.zed.lurin.process.cas.socket.client.ClientCas;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Llince
 */
@Dependent
public class CommndEventImpl implements ICommndEvent {

    ClientCas cli;

    @Deprecated
    @Override
    public String addProduct(CampaingCAS campaigns) {
        cli = new ClientCas();
        String response = "";
        for (Costumers cos : campaigns.getListCostumers()) {
            StringBuffer armingCommands = new StringBuffer();
            armingCommands.append(CommandCas.getCallConnection());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getTransactionId());
            armingCommands.append(campaigns.getIdTransaction());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getDestId());
            armingCommands.append(campaigns.getIdDest());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSourceId());
            armingCommands.append(campaigns.getIdSource());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getAddProduct());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getiMSProductId());
            armingCommands.append(cos.getId());
            armingCommands.append("\n");
            String strBiginDate = Validations.formatDate(CommandCas.getFormatDate(), campaigns.getIniDate());
            armingCommands.append(CommandCas.getBeginDate());
            armingCommands.append(strBiginDate);
            armingCommands.append("\n");
            String strEndDate = Validations.formatDate(CommandCas.getFormatDate(), campaigns.getEndDate());
            armingCommands.append(CommandCas.getEndDate());
            armingCommands.append(strEndDate);
            boolean ping = cli.connection(campaigns.getHostCas(), campaigns.getPortCas());
            if (ping) {
                cli.startClient();
                cli.sendCommand(armingCommands.toString());
                response = cli.getResponseCas();
                cli.closeClient();
            } else {
                return response = "500";
            }
        }
        return response;
    }

    @Deprecated
    @Override
    public String cancellProduct(CampaingCAS campaigns) {
        cli = new ClientCas();
        String response = "";
        for (Costumers cos : campaigns.getListCostumers()) {
            StringBuffer armingCommands = new StringBuffer();
            armingCommands.append(CommandCas.getCallConnection());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getTransactionId());
            armingCommands.append(campaigns.getIdTransaction());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getDestId());
            armingCommands.append(campaigns.getIdDest());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSourceId());
            armingCommands.append(campaigns.getIdSource());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getCancellationProduct());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getiMSProductId());
            armingCommands.append(cos.getId());
            armingCommands.append("\n");
            boolean ping = cli.connection(campaigns.getHostCas(), campaigns.getPortCas());
            if (ping) {
                cli.startClient();
                cli.sendCommand(armingCommands.toString());
                response = cli.getResponseCas();
                cli.closeClient();
            } else {
                return response = "500";
            }
        }
        return response;
    }

    @Deprecated
    @Override
    public String updateProduct(CampaingCAS campaigns) {
        cli = new ClientCas();
        String response = "";
        for (Costumers cos : campaigns.getListCostumers()) {
            StringBuffer armingCommands = new StringBuffer();
            armingCommands.append(CommandCas.getCallConnection());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getTransactionId());
            armingCommands.append(campaigns.getIdTransaction());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getDestId());
            armingCommands.append(campaigns.getIdDest());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSourceId());
            armingCommands.append(campaigns.getIdSource());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getReactivationProduct());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getiMSProductId());
            armingCommands.append(cos.getId());
            boolean ping = cli.connection(campaigns.getHostCas(), campaigns.getPortCas());
            if (ping) {
                cli.startClient();
                cli.sendCommand(armingCommands.toString());
                response = cli.getResponseCas();
                cli.closeClient();
            } else {
                return response = "500";
            }
        }
        return response;
    }

    @Deprecated
    @Override
    public String suspendProduct(CampaingCAS campaigns) {
        cli = new ClientCas();
        String response = "";
        for (Costumers cos : campaigns.getListCostumers()) {
            StringBuffer armingCommands = new StringBuffer();
            armingCommands.append(CommandCas.getCallConnection());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getTransactionId());
            armingCommands.append(campaigns.getIdTransaction());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getDestId());
            armingCommands.append(campaigns.getIdDest());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSourceId());
            armingCommands.append(campaigns.getIdSource());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSuspensionProduct());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getiMSProductId());
            armingCommands.append(cos.getId());
            armingCommands.append("\n");
            boolean ping = cli.connection(campaigns.getHostCas(), campaigns.getPortCas());
            if (ping) {
                cli.startClient();
                cli.sendCommand(armingCommands.toString());
                response = cli.getResponseCas();
                cli.closeClient();
            } else {
                return response = "500";
            }
        }
        return response;
    }

    @Deprecated
    @Override
    public String allCancellProduct(CampaingCAS campaigns) {
        cli = new ClientCas();
        String response = "";
        for (Costumers cos : campaigns.getListCostumers()) {
            StringBuffer armingCommands = new StringBuffer();
            armingCommands.append(CommandCas.getCallConnection());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getTransactionId());
            armingCommands.append(campaigns.getIdTransaction());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getDestId());
            armingCommands.append(campaigns.getIdDest());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getSourceId());
            armingCommands.append(campaigns.getIdSource());
            armingCommands.append("\n");
            armingCommands.append(CommandCas.getCancellationProduct());
            armingCommands.append("\n");
            boolean ping = cli.connection(campaigns.getHostCas(), campaigns.getPortCas());
            if (ping) {
                cli.startClient();
                cli.sendCommand(armingCommands.toString());
                response = cli.getResponseCas();
                cli.closeClient();
            } else {
                return response = "500";
            }
        }
        return response;
    }

    /**
     *
     * @param command
     * @param ipServerCas
     * @param portServerCas
     * @param portServerCas2
     * @param portServerCas3
     * @return
     * @throws Exception
     */
    @Override
    public String sendCommand(String command, String ipServerCas, String portServerCas, String portServerCas2, String portServerCas3) throws Exception {
        cli = new ClientCas();
        String response = "";

        boolean ping2 =false;
        boolean ping3 =false;

        boolean ping1 = cli.connection(ipServerCas, portServerCas);
        if (ping1) {
            cli.startClient();
            cli.sendCommand(command);
            response = cli.getResponseCas();
            cli.closeClient();
        }
        if(!ping1 && portServerCas2!=null){
            ping2 = cli.connection(ipServerCas, portServerCas2);
            if (ping2) {
                cli.startClient();
                cli.sendCommand(command);
                response = cli.getResponseCas();
                cli.closeClient();
            }
        }
        if(!ping2 && portServerCas3!=null){
            ping3 = cli.connection(ipServerCas, portServerCas3);
            if (ping3) {
                cli.startClient();
                cli.sendCommand(command);
                response = cli.getResponseCas();
                cli.closeClient();
            }
        }


        if(!ping1 && !ping2 && !ping3){
            throw new Exception(String.format("Error send command cas [%s]", command));
        }

        return response;
    }

}
