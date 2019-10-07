package br.ufma.lsdi.groupdefiner;

import lac.cnet.sddl.objects.GroupRegion;
import lac.cnet.sddl.objects.Message;
import lombok.val;

import java.util.HashSet;
import java.util.Set;

public class GroupSelector implements lac.cnet.groupdefiner.components.groupselector.GroupSelector {
    @Override
    public int getGroupType() {
        return 101;
    }

    @Override
    public Set<Integer> processGroups(Message message) {
        val groups = new HashSet<Integer>();
        groups.add(1);
        return groups;
    }

    @Override
    public void createGroup(GroupRegion groupRegion) {

    }
}
