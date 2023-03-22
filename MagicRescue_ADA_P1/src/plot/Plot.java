package plot;

public interface Plot {

    boolean hasItem();
    boolean hasMonster();

    boolean hasChance(String object, String monster);
    String getMonster();
    String getItem( String bag);

}
