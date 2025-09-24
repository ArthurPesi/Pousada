public class Nodo {
   //TODO: renomear para NodoLista
   private Nodo proximo;
   private String[] conteudo; 

   Nodo(String[] valores) {
      this.conteudo = valores.clone();
   }

   Nodo(String[] valores, Nodo proximo) {
      this.conteudo = valores.clone();
      this.proximo = proximo;
   }

   public String[] getConteudo() {
      return conteudo;
   }

   public Nodo getProximo() {
      return proximo;
   }

   public void setProximo(Nodo proximo) {
      this.proximo = proximo;
   }

   public void setConteudo(String[] valores) {
      this.conteudo = valores.clone();
   }
}
