package com.sundyn.util;

import java.net.*;

public class socketUdp
{
    private DatagramSocket cli;
    private byte[] ip;
    private int port;
    private byte[] msg;
    
    public socketUdp() throws SocketException {
        this.cli = new DatagramSocket(10002);
    }
    
    public socketUdp(final int port) throws SocketException {
        this.cli = new DatagramSocket(port);
    }
    
    public void send(final String sip, final int port, final String msg) {
        try {
            final String[] temp = sip.split("\\.");
            (this.ip = new byte[4])[0] = (byte)Integer.parseInt(temp[0]);
            this.ip[1] = (byte)Integer.parseInt(temp[1]);
            this.ip[2] = (byte)Integer.parseInt(temp[2]);
            this.ip[3] = (byte)Integer.parseInt(temp[3]);
            this.msg = new byte[2053];
            final byte[] m = msg.getBytes();
            int s = 0;
            for (int i = 0; i < m.length && i < 2048; ++i) {
                this.msg[i + 4] = m[i];
                s += m[i];
            }
            final byte[] f = this.intToByte(s);
            this.msg[2052] = f[0];
            final byte[] h = this.intToByte(m.length);
            for (int j = 0; j < h.length; ++j) {
                this.msg[j] = h[j];
            }
            final DatagramPacket pac = new DatagramPacket(this.msg, this.msg.length, InetAddress.getByAddress(this.ip), port);
            this.cli.send(pac);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        this.cli.close();
        this.cli = null;
    }
    
    public static void main(final String[] args) throws Exception {
        final socketUdp s = new socketUdp();
        s.send("192.168.100.33", 8001, "\u6211\u662fafafsa\u4e2d\u56fd\u4eba\r\nadfasdf");
    }
    
    public byte[] intToByte(final int i) {
        final byte[] bt = { (byte)(0xFF & i), (byte)((0xFF00 & i) >> 8), (byte)((0xFF0000 & i) >> 16), (byte)((0xFF000000 & i) >> 24) };
        return bt;
    }
}
