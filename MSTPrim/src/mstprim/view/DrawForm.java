package mstprim.view;

import com.sun.istack.internal.NotNull;
import mstprim.RandomGraphFactory;
import mstprim.alghoritm.model.Prim;
import mstprim.graph.model.Branch;
import mstprim.graph.model.Graph;
import mstprim.graph.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Created by Max on 5/11/14.
 */
public class DrawForm extends JFrame implements MouseListener, MouseMotionListener {
    private final GPanel panel;
    private final JButton btn_gen_graph;
    private final JButton btn_prim;
    private final JButton btn_clear;
    static private Graph graph;
    static private Prim prim;
    //
    final private Color COLOR_BRANCH_TAKEN = Color.GREEN;
    final private Color COLOR_BRANCH_NOT_TAKEN = Color.GRAY;
    final private Color COLOR_NODE = Color.YELLOW;
    final private Color COLOR_NODE_TEXT = Color.BLACK;
    final private Color COLOR_BRANCH_TEXT = Color.WHITE;
    final private Color COLOR_PANEL_BACKGROUND = Color.BLACK;
    final private double NODE_DIAMETER = 30;
    final private double NODE_DIAMETER_DIV_2 = NODE_DIAMETER / 2;
    final private Timer update_timer;
    final int MAX_NODES_AMOUNT = 8;
    final int MAX_BRANCHES_AMOUNT = MAX_NODES_AMOUNT * 2;
    final int MAX_BRANCH_LENGTH = 24;
    //
    private Node pressed_node = null;
    private int delta_x = 0;
    private int delta_y = 0;

    public DrawForm() {
        super("Visualisation");

        prim = new Prim();

        panel = new GPanel();
        btn_gen_graph = new JButton();
        btn_prim = new JButton();
        btn_clear = new JButton();
        panel.add(btn_gen_graph);
        panel.add(btn_prim);
        panel.add(btn_clear);

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        update_timer = new Timer(50, listener -> {
            repaint();
        });
        update_timer.start();

        btn_gen_graph.setLocation(10, 10);
        btn_gen_graph.setText("Gen graph");
        btn_gen_graph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    setGraph(RandomGraphFactory.generateRandomGraph(MAX_NODES_AMOUNT, MAX_BRANCHES_AMOUNT, MAX_BRANCH_LENGTH));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        btn_prim.setText("Prim");
        btn_prim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                prim.act(graph);
            }
        });

        btn_clear.setText("Clear");
        btn_clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                graph.clear();
            }
        });

        setSize(800, 600);
        setLocation(500, 200);
        setVisible(true);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void repaint(final int x, final int y, final int width, final int height) {
        super.repaint(x, y, width, height);
    }

    @Override
    public void repaint(final long time, final int x, final int y, final int width, final int height) {
        super.repaint(time, x, y, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        pressed_node = getNodeByCoords(e);

        if (pressed_node == null)
            return;

        pressed_node.setDragged(true);
        delta_x = e.getX() - (int)pressed_node.getX();
        delta_y = e.getY() - (int)pressed_node.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pressed_node == null)
            return;

        pressed_node.setDragged(false);
        pressed_node = null;
        delta_x = 0;
        delta_y = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public Node getNodeByCoords(@NotNull final MouseEvent e) {
        final int x = e.getX();
        final int y = e.getY();
        final List<Node> nodes = graph.getNodes();

       for (Node node: nodes)
            if (x >= node.getX() && y >= node.getY()
                && x <= node.getX() + NODE_DIAMETER
                && y <= node.getY() + NODE_DIAMETER)
            return node;

       return null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressed_node == null)
            return;

        if (pressed_node.isDragged()) {
            pressed_node.setX(e.getX() - delta_x);
            pressed_node.setY(e.getY() - delta_y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    static public void setGraph(@NotNull final Graph graph) {
        DrawForm.graph = graph;
    }

    public class GPanel extends JPanel {
        public GPanel() {
            addMouseListener(DrawForm.this);
            addMouseMotionListener(DrawForm.this);
            setBackground(COLOR_PANEL_BACKGROUND);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            synchronized (DrawForm.this) {
                redraw(g);
            }
        }

        protected void redraw(@NotNull Graphics g) {
            if (graph == null)
                return;

            @NotNull List<Node> nodes = graph.getNodes();
            @NotNull List<Branch> branches = graph.getBranches();

            branches.forEach(branch -> {
                final Node from = branch.getFrom();
                final Node to = branch.getTo();

                g.setColor(branch.isTaken() ? COLOR_BRANCH_TAKEN : COLOR_BRANCH_NOT_TAKEN);
                g.drawLine((int)(from.getX() + NODE_DIAMETER_DIV_2),
                           (int)(from.getY() + NODE_DIAMETER_DIV_2),
                           (int)(to.getX() + NODE_DIAMETER_DIV_2),
                           (int)(to.getY() + NODE_DIAMETER_DIV_2));

                int gX = (int) from.getX();
                int mX = (int) to.getX();
                int gY = (int) from.getY();
                int mY = (int) to.getY();

                if (mX > gX) {
                    int tmp = gX;
                    gX = mX;
                    mX = tmp;
                }
                if (mY > gY) {
                    int tmp = gY;
                    gY = mY;
                    mY = tmp;
                }

                String weight = branch.getLength() + "";
                char[] chars = weight.toCharArray();
                g.setColor(COLOR_BRANCH_TEXT);
                g.drawChars(chars, 0, weight.length(), mX + ((gX - mX) / 2), mY + ((gY - mY) / 2));
            });

            nodes.forEach(node -> {
                g.setColor(COLOR_NODE);
                g.fillOval((int) node.getX(),
                        (int) node.getY(),
                        (int) NODE_DIAMETER,
                        (int) NODE_DIAMETER);
                g.setColor(COLOR_NODE_TEXT);

                String text = node.getId() + "";
                Rectangle2D text_bounds = g.getFontMetrics().getStringBounds(text, g);
                int x = (int)(node.getX() - text_bounds.getX() / 2 + NODE_DIAMETER_DIV_2);
                int y = (int)(node.getY() - text_bounds.getY() / 2 + NODE_DIAMETER_DIV_2);

                g.drawString(text, x, y);
            });
        }
    }
}
