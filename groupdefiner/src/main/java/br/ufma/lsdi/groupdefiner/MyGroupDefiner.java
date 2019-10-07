package br.ufma.lsdi.groupdefiner;

import lac.cnet.groupdefiner.components.GroupDefiner;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class MyGroupDefiner  {

    public MyGroupDefiner() {

        val groupSelector = new GroupSelector();
        new GroupDefiner(groupSelector);

    }

}

