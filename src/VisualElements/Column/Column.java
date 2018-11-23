package VisualElements.Column;

import VisualElements.ElementParameters;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Column extends Group {

    private Rectangle rect;
    private Text text;
    private SimpleDoubleProperty rectWidth=new SimpleDoubleProperty(50);
    private static int count=0;

    public Column(double x,double y,Integer data,Color color){
        count++;
        checkCount();

        rect=new Rectangle();
        rect.widthProperty().bind(rectWidth);
        rect.setHeight(data*5+200);
        rect.setFill(color);
        rect.setArcHeight(ElementParameters.rectArcRadius);
        rect.setArcWidth(ElementParameters.rectArcRadius);
        rect.setLayoutX(-0.5*rect.getWidth());
        rect.setLayoutY(-rect.getHeight());

        text=new Text(data.toString());
        text.setFont(new Font(20));
        text.setLayoutX(-0.5*text.getBoundsInParent().getWidth());
        text.setLayoutY(-rect.getHeight()+text.getBoundsInParent().getHeight());

        this.getChildren().addAll(rect,text);
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public double getWidth(){
        return rectWidth.get();
    }

    private void checkCount(){

    }

}
