package plot;

public class MonsterPlot implements Plot{
    String monster;
    String object;

    static String HARP = "harp";
    static String POTION = "potion";
    static String CLOAK = "cloak";

    static String DRAGON = "dragon";
    static String TROLL = "troll";
    static String DOG = "dog";



    public MonsterPlot(){}

    public MonsterPlot(String object,String monster){
        this.monster = monster;
        this.object = object;
    }
    /**
     * @return
     */
    @Override
    public boolean hasItem() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean hasMonster() {
        return monster != null;
    }

    /**
     * @param object
     * @param monster
     * @return
     */
    @Override
    // Falta adicionar as unidades de tempo que demoram a passar o campo.
    public boolean hasChance(String object, String monster) {
        if(monster.equals(DOG) && object.equals(HARP))
            return true;
        if(monster.equals(TROLL) && object.equals(POTION))
            return true;
        if(object.equals(CLOAK))
            return true;
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getMonster() {
        return monster;
    }

    /**
     * @param bag
     * @return
     */
    @Override
    public String getItem(String bag) {
        return null;
    }


}
