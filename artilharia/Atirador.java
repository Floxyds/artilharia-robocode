package artilharia;
import robocode.*;
import robocode.util.Utils;

public class Atirador extends TeamRobot {

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            if (getRadarTurnRemaining() == 0) {
                setTurnRadarRight(360);
            }
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (isTeammate(e.getName())) return;

        double anguloAbsoluto = getHeading() + e.getBearing();

        
        double giroRadar = Utils.normalRelativeAngleDegrees(anguloAbsoluto - getRadarHeading());
        setTurnRadarRight(giroRadar * 2);

        
        setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing()));
        setAhead(e.getDistance() - 100);

        
        double giroCanhao = Utils.normalRelativeAngleDegrees(anguloAbsoluto - getGunHeading());
        setTurnGunRight(giroCanhao);

        double potencia = e.getDistance() < 150 ? 1.5 : 1;
        if (Math.abs(giroCanhao) < 10 && getGunHeat() == 0) {
            setFire(potencia);
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (isTeammate(e.getName())) {
            setBack(60);
            setTurnRight(90);
        }
    }
}