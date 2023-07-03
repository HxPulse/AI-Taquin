package model.moves;

import model.Agent;
import model.Position;
import model.path.Graph;

public class MoveStrategy {
    private int horizontally, vertically;
    public final static MoveStrategy _up = new MoveStrategy(-1, 0),
                                    _down = new MoveStrategy(1, 0),
                                    _left = new MoveStrategy(0, -1),
                                    _right = new MoveStrategy(0, 1);

    /**
     * Move strategy
     * @param v Vertical delta
     * @param h Horizontal delta
     */
    private MoveStrategy(int v, int h) {
        horizontally = h;
        vertically = v;
    }

    /**
     * Moves the agent given the horizontal and vertical deltas
     * @param agent Agent to move
     * @return true if the agent has been moved, false otherwise
     */
    public boolean move(Agent agent) {
        Position newPos = getNewPos(agent);
        if(Agent.getPlateau().checkPosition(newPos)
                && Agent.getPlateau().isFree(newPos)) {
            agent.setPosition(newPos);
            return true;
        }
        return false;
    }

    public Position getNewPos(Agent agent) {
        return new Position(agent.getPosition().getX() + vertically,
                agent.getPosition().getY() + horizontally);
    }

    static public Graph.direction getDirection(Position origin, Position target) {
        if(origin.manhattan(target) == 1) {
            if(origin.getX() > target.getX()) {
                return Graph.direction.up;
            }
            if(origin.getX() < target.getX()) {
                return Graph.direction.down;
            }
            if(origin.getY() > target.getY()) {
                return Graph.direction.left;
            }
            return Graph.direction.right;
        }
        return null;
    }
}
