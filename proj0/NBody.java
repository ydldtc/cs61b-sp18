public class NBody {
	public static double readRadius(String filename) {
		In in = new In(filename);
		int number_of_planets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int num = in.readInt();
		double radius = in.readDouble();
		Planet[] Planets = new Planet[num];
		for (int i = 0; i < num; i++) {
			double xp = in.readDouble();
			double yp= in.readDouble();
			double xv = in.readDouble();
			double yv = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planets[i] = new Planet(xp, yp, xv, yv, m, img);
		}
		return Planets;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double r = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		StdDraw.setScale(-r, r);
		StdDraw.enableDoubleBuffering();
		
		int num = planets.length;
		int time = 0;
		while (time < T) {
			double[] xForces = new double[num];
			double[] yForces = new double[num];
			for(int i = 0; i < num; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for(int i = 0; i < num; i++){
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet planet:planets) {
				planet.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		StdOut.printf("%d\n", num);
		StdOut.printf("%.2e\n", r);
		for (int i = 0; i < num; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName); 
		}
	}
}