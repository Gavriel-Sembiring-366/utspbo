package oop;

public class Purchase {
    public int table_no;
    public String table_kode;
    public String table_nama;
    public float table_harga;
    public int table_jumlah;
    public float table_total;
    
    public Purchase(){
            this.table_no = 1;
            this.table_kode = "";
            this.table_nama = "";
            this.table_harga = 0;
            this.table_jumlah = 0;
            this.table_total = 0;
        }
        
        public Purchase(int no, String kode, String nama, float harga, int jumlah, float total){
            this.table_no = no;
            this.table_kode = kode;
            this.table_nama = nama;
            this.table_harga = harga;
            this.table_jumlah = jumlah;
            this.table_total = total;
        }

        public Purchase(String kode, String nama, float harga){
            this.table_kode = kode;
            this.table_nama = nama;
            this.table_harga = harga;
        }

        public int getTable_no() {return table_no;}
        public String getTable_kode() {return table_kode;}
        public String getTable_nama() {return table_nama;}
        public float getTable_harga() {return table_harga;}
        public int getTable_jumlah() {return table_jumlah;}
        public float getTable_total() {return table_total;}

        public void setTable_total(int table_total) {this.table_total = table_total;}
        public void setTable_no(int table_no) {this.table_no = table_no;}
        public void setTable_kode(String table_kode) {this.table_kode = table_kode;}
        public void setTable_nama(String table_nama) {this.table_nama = table_nama;}
        public void setTable_harga(int table_harga) {this.table_harga = table_harga;}
        public void setTable_jumlah(int table_jumlah) {this.table_jumlah = table_jumlah;}
        
    }
