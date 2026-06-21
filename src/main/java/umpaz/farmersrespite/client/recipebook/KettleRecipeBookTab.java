package umpaz.farmersrespite.client.recipebook;

public enum KettleRecipeBookTab {
   DRINKS("drinks");

   public final String name;

   KettleRecipeBookTab(String name) {
      this.name = name;
   }

   public static KettleRecipeBookTab findByName(String name) {
      for (KettleRecipeBookTab value : values()) {
         if (value.name.equals(name)) {
            return value;
         }
      }

      return null;
   }

   @Override
   public String toString() {
      return this.name;
   }
}
