package com.addonis.demo.merge;



import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.AddonChangeDTO;
import com.addonis.demo.secondDB.secondModels.BinaryContent;
import com.addonis.demo.secondDB.secondServices.contracts.BinaryContentService;

import java.io.IOException;

/**
 *AddonMerge is a class used for updating addon - to map old addon and new addon.
 */
public class AddonMerger {

    public static Addon mergeTwoAddons(Addon oldAddon, AddonChangeDTO newAddon, BinaryContentService binaryContentService) throws IOException {
        oldAddon.setName(getNotNullValue(oldAddon.getName(), newAddon.getName()));
        oldAddon.setDescription(getNotNullValue(oldAddon.getDescription(), newAddon.getDescription()));

        if (newAddon.getFile() != null) {
            BinaryContent binaryContent = new BinaryContent();
            binaryContent.setDocName(newAddon.getFile().getOriginalFilename());
            binaryContent.setType(newAddon.getFile().getContentType());
            binaryContent.setFile(newAddon.getFile().getBytes());
            binaryContentService.create(binaryContent);
            oldAddon.setBinaryFile(binaryContent.getId());
        }
        return oldAddon;
    }

    public static <T> T getNotNullValue(T a, T b) {
        return b != null && a != null && !b.equals(a) ? b : a;
    }
}
