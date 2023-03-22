package plot;

public class EasyPlot implements Plot{

    String object;
    String bag;

    public EasyPlot(){
    }
    public EasyPlot(String object, String  bag){
        this.object = object;
        this.bag = bag;
    }

    /**
     * @return
     */
    @Override
    public boolean hasItem() {
        return object != null;
    }

    /**
     * @return
     */
    @Override
    public boolean hasMonster() {
        return false;
    }

    /**
     * @param object
     * @param monster
     * @return
     */
    @Override
    public boolean hasChance(String object, String monster) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getMonster() {
        return null;
    }

    /**
     * @param bag
     * @return
     */
    @Override
    public String getItem(String bag) {
        if(bag.equals(object)) {
        }
        else{
            bag = object;
        }
        return bag;
    }


}
