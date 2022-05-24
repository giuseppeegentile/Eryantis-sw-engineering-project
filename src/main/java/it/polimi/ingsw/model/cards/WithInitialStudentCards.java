package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CharacterEffects.Effect;
import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class WithInitialStudentCards extends CharacterCardModel{

    private static final long serialVersionUID = 8387008810019877844L;

    private final List<ColorPawns> students;
    public WithInitialStudentCards(int moneyCost, Effect effect, String characterId, List<ColorPawns> students) {
        super(moneyCost, effect, characterId);
        this.students = students;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    public void removeStudent(ColorPawns studentToRemove){
        this.students.remove(studentToRemove);
    }
}
