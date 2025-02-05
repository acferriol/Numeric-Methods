
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class Prop_Layout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {
        //
    }
    @Override
    public void removeLayoutComponent(Component comp) {
        //

    }
    @Override
    public void layoutContainer(Container parent) {
        parent.getComponent(0).setBounds(0, 0, 600, 830);
        parent.getComponent(1).setBounds(605,-1,760,830);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        //
        return null;
    }
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        //
        return null;
    }
}