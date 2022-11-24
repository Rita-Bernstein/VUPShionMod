package VUPShionMod.patches;

import VUPShionMod.stances.*;
import basemod.ReflectionHacks;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SFFPatches {

    @SpirePatch(
            clz = FileHandle.class,
            method = "read",
            paramtypez = {}
    )
    public static class SFFReadPatch {
        @SpirePrefixPatch
        public static SpireReturn<InputStream> returnStance(FileHandle _instance) {
            File file = ReflectionHacks.getPrivate(_instance,FileHandle.class,"file");
            String suffix = file.getName();
            suffix = suffix.substring(suffix.lastIndexOf('.') + 1);
            if (suffix.equals("sff")) {

                InputStream input = FileHandle.class.getResourceAsStream("/" + file.getPath().replace('\\', '/'));
                String key = "Rita";
                try {
                    byte[] allBytes = readBytes(input);

                    for (int i = 0; i < allBytes.length; i++) {
                        allBytes[i] ^= key.hashCode();
                    }
                    input = new ByteArrayInputStream(allBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return SpireReturn.Return(input) ;
            } else {
                return SpireReturn.Continue();
            }
        }
    }


    public static byte[] readBytes(InputStream in) throws IOException {
        byte[] temp = new byte[in.available()];
        byte[] result = new byte[0];

        int size = 0;
        while ((size = in.read(temp)) != -1) {
            byte[] readBytes = new byte[size];
            System.arraycopy(temp, 0, readBytes, 0, size);
            result = mergeArray(result, readBytes);
        }
        return result;


    }

    public static byte[] mergeArray(byte[]... a) {
        int index = 0;
        int sum = 0;

        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i].length;
        }

        byte[] result = new byte[sum];
        for (int i = 0; i < a.length; i++) {
            int lengthOne = a[i].length;
            if (lengthOne == 0) {
                continue;
            }

            System.arraycopy(a[i], 0, result, index, lengthOne);
            index = index + lengthOne;
        }
        return result;
    }

}
