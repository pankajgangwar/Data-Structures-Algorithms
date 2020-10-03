package com.pkumar7.spoj.graph;

import java.io.*;
import java.util.*;

class Holi {

    /*
    https://www.spoj.com/problems/HOLI/
    */
    private static Reader in;
    private static PrintWriter out;

    public static void main(String[] args) throws Exception {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        long t = in.nextInt();

        for (int i = 1; i <= t; i++) {
            int n = in.nextInt();
            LinkedList<Integer>[] graph = new LinkedList[n];
            LinkedList<Integer>[] weights = new LinkedList[n];

            for (int j = 0; j < n; j++) {
                graph[j] = new LinkedList<>();
                weights[j] = new LinkedList<>();
            }

            boolean[] visited = new boolean[n];
            Arrays.fill(visited, false);

            int []count = new int[n];
            Arrays.fill(count, 1);

            for (int j = 0; j < n - 1; j++) {
                int src = in.nextInt() - 1;
                int dst = in.nextInt() - 1;
                int weight = in.nextInt();
                graph[src].add(dst);
                graph[dst].add(src);
                weights[src].add(weight);
                weights[dst].add(weight);
            }
            dfs(graph, 0, visited, count, weights, n);
            out.print("Case #"+ i);
            out.print(": " + res);
            out.println();
            res = 0;
        }
        out.close();
        System.exit(0);
    }

    static long res = 0;
    public static void dfs(LinkedList<Integer>[] graph, int src,
                    boolean[] visited, int []count, LinkedList<Integer>[] weight, int n){
        visited[src] = true;
        for (int i = 0; i < graph[src].size(); i++) {
            int dst = graph[src].get(i);
            if(visited[dst])continue;
            dfs(graph, dst, visited, count, weight, n);
            res += 2L * Math.min(count[dst], n - count[dst]) * weight[src].get(i);
            count[src] += count[dst];
        }
    }

    /** Faster input **/
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
        public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
        public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
        public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
            while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
        public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
            if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
        public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
            if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
        public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
            if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
        private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
        private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
        public void close()throws IOException{if(din==null) return;din.close();}
    }

}