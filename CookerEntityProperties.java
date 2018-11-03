package ch.dentax.saoaddon.skills;

import ch.dentax.saoaddon.main.SAOMod;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CookerEntityProperties implements IExtendedEntityProperties {

    public final static String EXT_PROP_COOK = "CookerExtendedPlayer";

    int [] xpMaxByLvl = {20, 50, 100, 175, 300, 450, 600, 800, 1100, 1300, 1600, 2000, 2500, 3100, 3800, 4800, 6000, 7200, 9200, 10500};
    int level;

    private final EntityPlayer propPlayer;

    private int currentXp;

    public CookerEntityProperties(EntityPlayer player) {

        this.propPlayer = player;

        this.currentXp = 0;

        this.level = 0;

        this.xpMaxByLvl[level] = 100;

    }

    public static final void register(EntityPlayer player) {

        player.registerExtendedProperties(CookerEntityProperties.EXT_PROP_COOK, new CookerEntityProperties(player));

    }

    public static final CookerEntityProperties get(EntityPlayer player) {

        return (CookerEntityProperties) player.getExtendedProperties(EXT_PROP_COOK);

    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {

        NBTTagCompound properties = new NBTTagCompound();

        properties.setInteger("CurrentXp", this.currentXp);
        properties.setInteger("XpMax", this.xpMaxByLvl[this.level]);
        properties.setInteger("Level", this.level);

        compound.setTag(EXT_PROP_COOK, properties);

    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_COOK);
        this.currentXp = properties.getInteger("CurrentXp");
        this.level = properties.getInteger("Level");
        this.xpMaxByLvl[this.level] = properties.getInteger("XpMax");
        System.out.println("[Cooker] Xp : " + this.currentXp + "/" + this.xpMaxByLvl[this.level] + " and level :" + this.level);
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public boolean consumeXp(int amount) {
        boolean sufficient = amount <= this.currentXp;

        this.currentXp -= (amount < this.currentXp ? amount : this.currentXp);
        System.out.println("[Cooker] Xp : " + this.currentXp + "/" + this.xpMaxByLvl[this.level] + " and level :" + this.level);
        SAOMod.network.sendTo(new PacketSkills(String.valueOf(this.currentXp) + "/" + String.valueOf(this.xpMaxByLvl[this.level])), (EntityPlayerMP)propPlayer);
        return sufficient;
    }

    public int getCurrentXp() {

        return this.currentXp;

    }

    public boolean addXp(int amount) {

        //boolean sufficient = this.currentXp + amount < this.xpMaxByLvl[this.level];


        if(this.currentXp < this.xpMaxByLvl[this.level]){

            this.currentXp += amount;

        }
        else if (this.currentXp + amount == this.xpMaxByLvl[this.level]){

            level++;
            currentXp = 0;

        }
        else{
            this.currentXp += amount;

            while (this.currentXp    > this.xpMaxByLvl[this.level]){
                    this.currentXp -= this.xpMaxByLvl[this.level];
                    this.level++;
            }
        }

        System.out.println("[Cooker ADD] Xp : " + this.currentXp + "/" + this.xpMaxByLvl[this.level] + " and level :" + this.level + " " + this.xpMaxByLvl[this.level]);

        SAOMod.network.sendTo(new PacketSkills("caca"), (EntityPlayerMP)propPlayer);

        return true;
    }


    public int getLevel(){

        return this.level;

    }

    public int getMaxXpByLevel(int level){

        return xpMaxByLvl[level];

    }
}
