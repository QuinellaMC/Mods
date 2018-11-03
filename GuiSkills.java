package ch.dentax.saoaddon.gui;

import ch.dentax.saoaddon.skills.CookerEntityProperties;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class GuiSkills extends Gui {

    int xpBarWidth;



    public GuiSkills(Minecraft mc) {

        this.mc = mc;

    }


    private Minecraft mc;

    private static final ResourceLocation texturepath = new ResourceLocation("forge","saoaddon:textures/gui/forge_xp_empty.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent event){

        if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
        {
            return;
        }




        CookerEntityProperties props = CookerEntityProperties.get(this.mc.thePlayer);

        if (props == null || props.getMaxXpByLevel(props.getLevel()) == 0 ){

            return;

        }

        int xPos = event.resolution.getScaledWidth() - 70;
        int yPos = 10 ;

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glDisable(GL11.GL_LIGHTING);

        this.mc.getTextureManager().bindTexture(texturepath);

        this.drawTexturedModalRect(xPos, yPos, 0, 0, 64, 32);

        //xpBarWidth = (int)(((float) props.getCurrentXp() / props.getMaxXpByLevel(props.getLevel())) * 25);
        //System.out.println("[GUI XP] Current xp bar width: " + xpBarWidth);

        this.drawTexturedModalRect(xPos, yPos, 0, 63, xpBarWidth, 2);

        mc.ingameGUI.drawString(mc.fontRenderer, String.valueOf(props.getLevel()) , xPos + 60, yPos + 8, 0x494949);

    }

}
