package ch.dentax.saoaddon.skills;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketSkills implements IMessage {

    private String text;

    public PacketSkills() { }

    public PacketSkills(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, text);

    }

    public static class Handler implements IMessageHandler <PacketSkills, IMessage> {

        @Override
        public IMessage onMessage(PacketSkills message, MessageContext ctx) {
            System.out.println("Message reçu : " + message.text + " par" + ctx.getServerHandler().playerEntity.getDisplayName());
            return null;
        }
    }
}
