package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import com.example.abdelgani.muehle.Classes.Node;
import com.example.abdelgani.muehle.Classes.Tile;

import java.util.ArrayList;

public class SinglePlayerActivity extends AppCompatActivity {

	private static final String TAG = "__________";
	private enum Phase {EARLY_GAME, MID_GAME, LATE_GAME}

	Node node_outer_topLeft, node_outer_topMid, node_outer_topRight, node_outer_midLeft, node_outer_midRight, node_outer_botLeft, node_outer_botMid, node_outer_botRight;
	Node node_middle_topLeft, node_middle_topMid, node_middle_topRight, node_middle_midLeft, node_middle_midRight, node_middle_botLeft, node_middle_botMid, node_middle_botRight;
	Node node_inner_topLeft, node_inner_topMid, node_inner_topRight, node_inner_midLeft, node_inner_midRight, node_inner_botLeft, node_inner_botMid, node_inner_botRight;
	Node[][][] nodes = new Node[3][3][3];
    Tile tileP1_1, tileP1_2, tileP1_3, tileP1_4, tileP1_5, tileP1_6, tileP1_7, tileP1_8, tileP1_9;
	Tile tileP2_1, tileP2_2, tileP2_3, tileP2_4, tileP2_5, tileP2_6, tileP2_7, tileP2_8, tileP2_9;
	Button[] player1Tiles, player2Tiles;
	int offset;
	int whiteTilesInHand = 9;
	int blackTilesInHand = 9;
	Phase currentPhase = Phase.EARLY_GAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );
		Log.d(TAG,"Singleplayer startup");

        try {
        	//region misc
			offset = (int)((getResources().getDimension(R.dimen.nodeSize) - getResources().getDimension(R.dimen.tileSize) + 0.5)/2);
			//endregion
			//region assign nodes
			nodes[0][0][0] = findViewById(R.id.node_outer_topLeft);
			nodes[0][0][1] = findViewById(R.id.node_outer_topMid);
			nodes[0][0][2] = findViewById(R.id.node_outer_topRight);
			nodes[0][1][0] = findViewById(R.id.node_outer_midLeft);
			nodes[0][1][2] = findViewById(R.id.node_outer_midRight);
			nodes[0][2][0] = findViewById(R.id.node_outer_botLeft);
			nodes[0][2][1] = findViewById(R.id.node_outer_botMid);
			nodes[0][2][2] = findViewById(R.id.node_outer_botRight);
			nodes[1][0][0] = findViewById(R.id.node_middle_topLeft);
			nodes[1][0][1] = findViewById(R.id.node_middle_topMid);
			nodes[1][0][2] = findViewById(R.id.node_middle_topRight);
			nodes[1][1][0] = findViewById(R.id.node_middle_midLeft);
			nodes[1][1][2] = findViewById(R.id.node_middle_midRight);
			nodes[1][2][0] = findViewById(R.id.node_middle_botLeft);
			nodes[1][2][1] = findViewById(R.id.node_middle_botMid);
			nodes[1][2][2] = findViewById(R.id.node_middle_botRight);
			nodes[2][0][0] = findViewById(R.id.node_inner_topLeft);
			nodes[2][0][1] = findViewById(R.id.node_inner_topMid);
			nodes[2][0][2] = findViewById(R.id.node_inner_topRight);
			nodes[2][1][0] = findViewById(R.id.node_inner_midLeft);
			nodes[2][1][2] = findViewById(R.id.node_inner_midRight);
			nodes[2][2][0] = findViewById(R.id.node_inner_botLeft);
			nodes[2][2][1] = findViewById(R.id.node_inner_botMid);
			nodes[2][2][2] = findViewById(R.id.node_inner_botRight);
			//endregion
			//region set DragListeners to nodes
			nodes[0][0][0].setOnDragListener(dragListener);
			nodes[0][0][1].setOnDragListener(dragListener);
			nodes[0][0][2].setOnDragListener(dragListener);
			nodes[0][1][0].setOnDragListener(dragListener);
			nodes[0][1][2].setOnDragListener(dragListener);
			nodes[0][2][0].setOnDragListener(dragListener);
			nodes[0][2][1].setOnDragListener(dragListener);
			nodes[0][2][2].setOnDragListener(dragListener);
			nodes[1][0][0].setOnDragListener(dragListener);
			nodes[1][0][1].setOnDragListener(dragListener);
			nodes[1][0][2].setOnDragListener(dragListener);
			nodes[1][1][0].setOnDragListener(dragListener);
			nodes[1][1][2].setOnDragListener(dragListener);
			nodes[1][2][0].setOnDragListener(dragListener);
			nodes[1][2][1].setOnDragListener(dragListener);
			nodes[1][2][2].setOnDragListener(dragListener);
			nodes[2][0][0].setOnDragListener(dragListener);
			nodes[2][0][1].setOnDragListener(dragListener);
			nodes[2][0][2].setOnDragListener(dragListener);
			nodes[2][1][0].setOnDragListener(dragListener);
			nodes[2][1][2].setOnDragListener(dragListener);
			nodes[2][2][0].setOnDragListener(dragListener);
			nodes[2][2][1].setOnDragListener(dragListener);
			nodes[2][2][2].setOnDragListener(dragListener);
			//endregiont
			//region set Node neighbours
			node_outer_topLeft.setNeighbourNodes(node_outer_topMid, node_outer_midLeft);
			node_outer_topMid.setNeighbourNodes(node_outer_topLeft, node_outer_topRight, node_middle_topMid);
			node_outer_topRight.setNeighbourNodes(node_outer_topMid, node_outer_midRight);
			node_outer_midLeft.setNeighbourNodes(node_outer_topLeft, node_outer_botLeft, node_middle_midLeft);
			node_outer_midRight.setNeighbourNodes(node_outer_topRight, node_outer_botRight, node_middle_midRight);
			node_outer_botLeft.setNeighbourNodes(node_outer_midLeft, node_outer_botMid);
			node_outer_botMid.setNeighbourNodes(node_outer_botLeft, node_outer_botRight, node_middle_botMid);
			node_outer_botRight.setNeighbourNodes(node_outer_botMid, node_outer_midRight);
			node_middle_topLeft.setNeighbourNodes(node_middle_topMid, node_middle_midLeft);
			node_middle_topMid.setNeighbourNodes(node_outer_topMid, node_middle_topLeft, node_middle_topRight, node_inner_topMid);
			node_middle_topRight.setNeighbourNodes(node_middle_topMid, node_middle_midRight);
			node_middle_midLeft.setNeighbourNodes(node_outer_midLeft, node_middle_topLeft, node_middle_botLeft, node_inner_midLeft);
			node_middle_midRight.setNeighbourNodes(node_outer_midRight, node_middle_topRight, node_middle_botRight, node_inner_midRight);
			node_middle_botLeft.setNeighbourNodes(node_middle_midLeft, node_middle_botMid);
			node_middle_botMid.setNeighbourNodes(node_outer_botMid, node_middle_botLeft, node_middle_botRight, node_inner_botMid);
			node_middle_botRight.setNeighbourNodes(node_middle_midRight, node_middle_botMid);
			node_inner_topLeft.setNeighbourNodes(node_inner_topMid, node_inner_midLeft);
			node_inner_topMid.setNeighbourNodes(node_middle_topMid, node_inner_topLeft, node_inner_topRight);
			node_inner_topRight.setNeighbourNodes(node_inner_topMid, node_inner_midRight);
			node_inner_midLeft.setNeighbourNodes(node_middle_midLeft, node_inner_topLeft, node_inner_botLeft);
			node_inner_midRight.setNeighbourNodes(node_middle_midRight, node_inner_topRight, node_inner_botRight);
			node_inner_botLeft.setNeighbourNodes(node_inner_midLeft, node_inner_botMid);
			node_inner_botMid.setNeighbourNodes(node_middle_botMid, node_inner_botLeft,node_inner_botRight);
			node_inner_botRight.setNeighbourNodes(node_inner_midRight, node_inner_botMid);
			//endregion
			//region assign millHelpers
			node_outer_topLeft.setMillHelper(111);
			node_outer_topMid.setMillHelper(112);
			node_outer_topRight.setMillHelper(113);
			node_outer_midLeft.setMillHelper(121);
			node_outer_midRight.setMillHelper(123);
			node_outer_botLeft.setMillHelper(131);
			node_outer_botMid.setMillHelper(132);
			node_outer_botRight.setMillHelper(133);
			node_middle_topLeft.setMillHelper(211);
			node_middle_topMid.setMillHelper(212);
			node_middle_topRight.setMillHelper(213);
			node_middle_midLeft.setMillHelper(221);
			node_middle_midRight.setMillHelper(223);
			node_middle_botLeft.setMillHelper(231);
			node_middle_botMid.setMillHelper(232);
			node_middle_botRight.setMillHelper(233);
			node_inner_topLeft.setMillHelper(311);
			node_inner_topMid.setMillHelper(312);
			node_inner_topRight.setMillHelper(313);
			node_inner_midLeft.setMillHelper(321);
			node_inner_midRight.setMillHelper(323);
			node_inner_botLeft.setMillHelper(331);
			node_inner_botMid.setMillHelper(332);
			node_inner_botRight.setMillHelper(333);
			//endregion
			// region assign tiles
			tileP1_1 = findViewById(R.id.tileP1_1);
			tileP1_2 = findViewById(R.id.tileP1_2);
			tileP1_3 = findViewById(R.id.tileP1_3);
			tileP1_4 = findViewById(R.id.tileP1_4);
			tileP1_5 = findViewById(R.id.tileP1_5);
			tileP1_6 = findViewById(R.id.tileP1_6);
			tileP1_7 = findViewById(R.id.tileP1_7);
			tileP1_8 = findViewById(R.id.tileP1_8);
			tileP1_9 = findViewById(R.id.tileP1_9);
			tileP2_1 = findViewById(R.id.tileP2_1);
			tileP2_2 = findViewById(R.id.tileP2_2);
			tileP2_3 = findViewById(R.id.tileP2_3);
			tileP2_4 = findViewById(R.id.tileP2_4);
			tileP2_5 = findViewById(R.id.tileP2_5);
			tileP2_6 = findViewById(R.id.tileP2_6);
			tileP2_7 = findViewById(R.id.tileP2_7);
			tileP2_8 = findViewById(R.id.tileP2_8);
			tileP2_9 = findViewById(R.id.tileP2_9);
			//endregion
			//region set LongClickListeners to tiles
			tileP1_1.setOnLongClickListener(longClickListener);
			tileP1_2.setOnLongClickListener(longClickListener);
			tileP1_3.setOnLongClickListener(longClickListener);
			tileP1_4.setOnLongClickListener(longClickListener);
			tileP1_5.setOnLongClickListener(longClickListener);
			tileP1_6.setOnLongClickListener(longClickListener);
			tileP1_7.setOnLongClickListener(longClickListener);
			tileP1_8.setOnLongClickListener(longClickListener);
			tileP1_9.setOnLongClickListener(longClickListener);
			tileP2_1.setOnLongClickListener(longClickListener);
			tileP2_2.setOnLongClickListener(longClickListener);
			tileP2_3.setOnLongClickListener(longClickListener);
			tileP2_4.setOnLongClickListener(longClickListener);
			tileP2_5.setOnLongClickListener(longClickListener);
			tileP2_6.setOnLongClickListener(longClickListener);
			tileP2_7.setOnLongClickListener(longClickListener);
			tileP2_8.setOnLongClickListener(longClickListener);
			tileP2_9.setOnLongClickListener(longClickListener);
			//endregion
			player1Tiles = new Button[]{tileP1_1, tileP1_2, tileP1_3, tileP1_4, tileP1_5, tileP1_6, tileP1_7, tileP1_8, tileP1_9};
			player2Tiles = new Button[]{tileP2_1, tileP2_2, tileP2_3, tileP2_4, tileP2_5, tileP2_6, tileP2_7, tileP2_8, tileP2_9};

		}catch (Exception exc){
        	exc.getMessage();
		}
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			Log.d(TAG,"enter onLongClick" + v.getId());
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
			View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
			v.startDrag(clipData, dragShadow, v, 0);
			return true;
		}
	};

    Node.OnDragListener dragListener = new Node.OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				Log.d(TAG, "enter onDrag" + v.getId());
				switch(currentPhase)
				{
					case EARLY_GAME:
						return earlyGame(v, event);
					case MID_GAME:
						return midGame(v,event);
					case LATE_GAME:
						return lateGame(v,event);
						default:
							break;
				}
				return true;
			}
    };

    private boolean earlyGame(View v, DragEvent event){
		Node node = (Node) v;
		Tile tile = (Tile)event.getLocalState();
		switch (event.getAction())
		{
			case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
				return node.isFree() && tile.getCurrentNode() == null;
			case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED
				break;
			case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
				move(node, tile);
				checkForMill(node, tile);
				break;
			case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
				v.setBackground(null);
				return event.getResult();

			case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
				Drawable background = tile.getBackground().getConstantState().newDrawable();
				if (background != null)
					v.setBackground(background);
				break;
			case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
				v.setBackground(null);
				break;
			default:
				//CodeSnippets für später:
				tileP1_1.setEnabled(false);
				break;
		}
    	return true;
	}
    private boolean midGame(View v, DragEvent event){
		Node node = (Node) v;
		Tile tile = (Tile)event.getLocalState();
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
				return node.isFree() && tile.nodeIsNeighbour(node);
			case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED
				break;
			case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
				move(node, tile);
				break;
			case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
				v.setBackground(null);
				return event.getResult();
			case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
				Drawable background = tile.getBackground().getConstantState().newDrawable();
				if (background != null)
					v.setBackground(background);
				break;
			case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
				v.setBackground(null);
				break;
			default:
				//CodeSnippets für später:
				tileP1_1.setEnabled(false);
				break;
		}
		return true;
	}


	private boolean lateGame(View v, DragEvent event){

    	return true;
	}


    private void move(Node node, Tile tile){
		tile.animate().x(node.getX() + offset).y(node.getY() + offset).setDuration(500).start();
		node.setOccupied(true);
		if ( tile.getCurrentNode() != null)
			tile.getCurrentNode().setOccupied(false);
		tile.setCurrentNode(node);
    }

	private void checkForMill(Node node, Tile tile) {
		int i = tile.getCurrentNode().getMillHelper();
		ArrayList<Node> neighbours = tile.getCurrentNode().getNeighbourNodes();
		for (Node neighbour : neighbours) {
			int j = neighbour.getMillHelper();
			int direction = i - j;
			switch(direction) {
				case 1:

			}
		}
	}

    public void onBackPressed(){
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
		dlgBuilder.setMessage("Sind sie sicher dass Sie das Spiel verlassen wollen?");
		dlgBuilder.setCancelable(true);
		dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Toast.makeText(SinglePlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
				finish();
				//Intent secondAct = new Intent( SinglePlayerActivity.this, SecondActivity.class );
				//startActivity( secondAct );
			}
		});
		dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog alert = dlgBuilder.create();
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
		alert.show();
	}
/*
	// Zurück zur SecondActivity fall die Rückwärts taste gedrückt wird.
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);

			dlgBuilder.setMessage("Sind sie sicher dass Sie das Spiel verlassen wollen?");

			dlgBuilder.setCancelable(true);
			dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Toast.makeText(SinglePlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
					Intent secondAct = new Intent( SinglePlayerActivity.this, SecondActivity.class );
					startActivity( secondAct );
				}
			});
			dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			AlertDialog alert = dlgBuilder.create();
			alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
			alert.show();
		}
		return true;
	}
*/
	private void ShowToast(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
		toast.show();
	}

}
/*
			node_outer_topLeft = findViewById(R.id.node_outer_topLeft);
			node_outer_topMid = findViewById(R.id.node_outer_topMid);
			node_outer_topRight = findViewById(R.id.node_outer_topRight);
			node_outer_midLeft = findViewById(R.id.node_outer_midLeft);
			node_outer_midRight = findViewById(R.id.node_outer_midRight);
			node_outer_botLeft = findViewById(R.id.node_outer_botLeft);
			node_outer_botMid = findViewById(R.id.node_outer_botMid);
			node_outer_botRight = findViewById(R.id.node_outer_botRight);
			node_middle_topLeft = findViewById(R.id.node_middle_topLeft);
			node_middle_topMid = findViewById(R.id.node_middle_topMid);
			node_middle_topRight = findViewById(R.id.node_middle_topRight);
			node_middle_midLeft = findViewById(R.id.node_middle_midLeft);
			node_middle_midRight = findViewById(R.id.node_middle_midRight);
			node_middle_botLeft = findViewById(R.id.node_middle_botLeft);
			node_middle_botMid = findViewById(R.id.node_middle_botMid);
			node_middle_botRight = findViewById(R.id.node_middle_botRight);
			node_inner_topLeft = findViewById(R.id.node_inner_topLeft);
			node_inner_topMid = findViewById(R.id.node_inner_topMid);
			node_inner_topRight = findViewById(R.id.node_inner_topRight);
			node_inner_midLeft = findViewById(R.id.node_inner_midLeft);
			node_inner_midRight = findViewById(R.id.node_inner_midRight);
			node_inner_botLeft = findViewById(R.id.node_inner_botLeft);
			node_inner_botMid = findViewById(R.id.node_inner_botMid);
			node_inner_botRight = findViewById(R.id.node_inner_botRight);

			node_outer_topLeft	.setOnDragListener(dragListener);
			node_outer_topMid	.setOnDragListener(dragListener);
			node_outer_topRight	.setOnDragListener(dragListener);
			node_outer_midLeft	.setOnDragListener(dragListener);
			node_outer_midRight	.setOnDragListener(dragListener);
			node_outer_botLeft	.setOnDragListener(dragListener);
			node_outer_botMid	.setOnDragListener(dragListener);
			node_outer_botRight	.setOnDragListener(dragListener);
			node_middle_topLeft	.setOnDragListener(dragListener);
			node_middle_topMid	.setOnDragListener(dragListener);
			node_middle_topRight.setOnDragListener(dragListener);
			node_middle_midLeft	.setOnDragListener(dragListener);
			node_middle_midRight.setOnDragListener(dragListener);
			node_middle_botLeft	.setOnDragListener(dragListener);
			node_middle_botMid	.setOnDragListener(dragListener);
			node_middle_botRight.setOnDragListener(dragListener);
			node_inner_topLeft	.setOnDragListener(dragListener);
			node_inner_topMid	.setOnDragListener(dragListener);
			node_inner_topRight	.setOnDragListener(dragListener);
			node_inner_midLeft	.setOnDragListener(dragListener);
			node_inner_midRight	.setOnDragListener(dragListener);
			node_inner_botLeft	.setOnDragListener(dragListener);
			node_inner_botMid	.setOnDragListener(dragListener);
			node_inner_botRight	.setOnDragListener(dragListener);
*/