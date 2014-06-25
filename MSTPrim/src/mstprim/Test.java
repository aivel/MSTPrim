package mstprim;

import mstprim.view.DrawForm;

/**
 * Demo program to test out Prim's algorithm.
 *
 * Created by Max on 4/27/14.
 */
public class Test {
    public static void main(String args[]) {
        DrawForm df = new DrawForm();

        try {
            df.setGraph(RandomGraphFactory.getGraph());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
