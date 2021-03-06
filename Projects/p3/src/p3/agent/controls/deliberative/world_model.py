from planner.model_planner import ModelPlanner
from psa import util

from .move_operator import MoveOperator


class WorldModel(ModelPlanner):

    def __init__(self):
        self._changed = False
        self._elements = {}
        self._state = ()
        self._states = ()
        self._operators = []

        for angle in util.dirmov():
            self._operators.append(MoveOperator(self, angle))

    @property
    def changed(self):
        return self._changed

    @property
    def state(self):
        return self._state

    @property
    def operators(self):
        return self._operators

    @property
    def states(self):
        return self._states

    def obtain_elements(self, state):
        return self._elements.get(state)

    def update(self, perception):
        self._state = perception.posicao

        if self._elements != perception.imagem:
            self._states = perception.imagem.keys()
            self._elements = perception.imagem
            self._changed = True
        else:
            self._changed = False

    def show(self, vismod):
        objects = ["alvo", "obstaculo"]
        targets = {key: value for (key, value) in self._elements.items() if value in objects}
        vismod.elementos(targets)
