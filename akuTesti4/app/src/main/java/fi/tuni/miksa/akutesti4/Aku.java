package fi.tuni.miksa.akutesti4;
//18.2.2019

public class Aku {

        private String kirjanNumero;

        private String kirjanNimi;

        public String getKirjanNimi() {
            return kirjanNimi;
        }

        public void setKirjanNimi(String kirjanNimi) {
            this.kirjanNimi = kirjanNimi;
        }

        public String getKirjanNumero() {
            return kirjanNumero;
        }

        public void setKirjanNumero(String kirjanNumero) {
            this.kirjanNumero = kirjanNumero;
        }

        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return kirjanNumero+". "+kirjanNimi;
        }


}
