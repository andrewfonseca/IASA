package game.npc;

import machine.intelligence.StateMachineBehavior;
import game.environment.EnvironmentEvent;
import game.environment.Stimulus;
import game.npc.behaviors.Combat;
import game.npc.behaviors.Defend;
import game.npc.behaviors.Inspection;
import game.npc.behaviors.Patrol;
import machine.State;
import machine.StateMachine;

public class CharacterBehavior extends StateMachineBehavior {

    @Override
    protected StateMachine<Stimulus> start() {
        State<Stimulus> patrol = new State<Stimulus>("PATROL");
        State<Stimulus> inspection= new State<Stimulus>("INSPECTION");
        State<Stimulus> defend = new State<Stimulus>("DEFEND");
        State<Stimulus> combat = new State<Stimulus>("COMBAT");

        patrol.add_transition(EnvironmentEvent.ENEMY, defend);
        patrol.add_transition(EnvironmentEvent.NOISE, inspection);

        inspection.add_transition(EnvironmentEvent.ENEMY, defend);
        inspection.add_transition(EnvironmentEvent.SILENCE, patrol);

        defend.add_transition(EnvironmentEvent.FLEE, inspection);
        defend.add_transition(EnvironmentEvent.ENEMY, combat);

        combat.add_transition(EnvironmentEvent.ENEMY, combat);
        combat.add_transition(EnvironmentEvent.FLEE, patrol);
        combat.add_transition(EnvironmentEvent.VICTORY, patrol);
        combat.add_transition(EnvironmentEvent.DEFEAT, patrol);

        super.add_behavior(patrol, new Patrol());
        super.add_behavior(inspection, new Inspection());
        super.add_behavior(defend, new Defend());
        super.add_behavior(combat, new Combat());

        return new StateMachine<Stimulus>(patrol);
    }
}
