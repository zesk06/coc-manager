package com.boobaskaya.cocclanmanager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.boobaskaya.cocclanmanager.model.Building;
import com.boobaskaya.cocclanmanager.model.BuildingType;
import com.boobaskaya.cocclanmanager.model.Cannon;
import com.boobaskaya.cocclanmanager.model.Clan;
import com.boobaskaya.cocclanmanager.model.Player;
import com.boobaskaya.cocclanmanager.tools.JAXBTools;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class FXMLController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger("COC");

    private static final File clanFile = new File("clan.xml");

    private Clan clan;

    @FXML
    private TableView<Player> memberTable;
    @FXML
    private TableColumn<Player, String> pseudoColumn;
    @FXML
    private TableColumn<Player, Integer> hdvColumn;
    @FXML
    private TableView<Building> buildingTable;
    @FXML
    private ComboBox<Player> cbMember;
    @FXML
    private ComboBox<Integer> cbHdv;
    @FXML
    private ComboBox<BuildingType> cbBuilding;
    @FXML
    private ComboBox<Integer> cbLevel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Clan page
        memberTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memberTable.setEditable(true);
        pseudoColumn.setCellFactory(TextFieldTableCell.<Player>forTableColumn());
        pseudoColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player, String> event) {
                System.out.println("New pseudo for player : " + event.getNewValue());
                event.getRowValue().setPseudo(event.getNewValue());
            }
        });
        // Member page
		buildingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cbBuilding.setItems(FXCollections.observableArrayList(BuildingType.values()));
        cbBuilding.setValue(BuildingType.CANNON);
        cbLevel.setItems(FXCollections.observableArrayList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));

        cbLevel.setValue(1);
		// buttonCell permit to customize the combox box cell when displayed
		cbMember.setButtonCell(new ListCell<Player>() {
			@Override
			public void updateItem(Player item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.getPseudo());
				}
			}
		});
		// cellFactory permits to customize combobox cells when dropped down
		cbMember.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
			@Override
			public ListCell<Player> call(ListView<Player> arg0) {
				ListCell<Player> cells = new ListCell<Player>() {
					@Override
					public void updateItem(Player item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getPseudo());
						}
					}
				};
				return cells;
			}
		});
		cbHdv.setItems(FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));

        //
        setClan(new Clan());

        reloadFile();
    }

    private void reloadFile() {
        if (clanFile.exists()) {
            try {
                setClan(JAXBTools.fromFile(clanFile, Clan.class));
            } catch (JAXBException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setClan(Clan clan) {
        this.clan = clan;
        memberTable.setItems(clan.getMembers());
        cbMember.setItems(clan.getMembers());
        //simulate cbMember action
        if (clan.getMembers().size() > 0) {
            cbMember.setValue(clan.getMembers().get(0));
            this.cbMemberAction(null);
        }
    }

    @FXML
    private void addMember(ActionEvent event) {
        clan.getMembers().add(new Player());
    }

    @FXML
    private void rmMember(ActionEvent event) {
        //retrieve the index of the item to remove
        memberTable.getSelectionModel().getSelectedItems().stream().forEach((p) -> {
            clan.getMembers().remove(p);
        });
    }

    @FXML
    private void load(ActionEvent event) {
        reloadFile();
    }

    @FXML
    private void save(ActionEvent event) {
        try {
            JAXBTools.toFile(this.clan, Clan.class, clanFile);
        } catch (JAXBException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cbMemberAction(ActionEvent event) {
        System.out.println("Member " + cbMember.getValue() + " selected");
        Player selectedPlayer = cbMember.getValue();
		if (selectedPlayer != null) {
			buildingTable.setItems(selectedPlayer.getBuildings());
			cbHdv.setValue(selectedPlayer.getHdv());
		}
    }

    @FXML
    private void cbHdvAction(ActionEvent event) {
		LOGGER.info("cbHdv action to " + cbHdv.getValue());
		cbMember.getValue().setHdv(cbHdv.getValue());
    }

    @FXML
    private void cbBuildingAction(ActionEvent event) {
        System.out.println("Building selected");
        //change accessible levels?
    }

    @FXML
    private void cbLevelAction(ActionEvent event) {
        //nothing to do
    }

    @FXML
    private void addBuilding(ActionEvent event) {
        switch (cbBuilding.getValue()) {
            case CANNON:
			cbMember.getValue().addBuilding(new Cannon(cbLevel.getValue()));
                break;
            case HDV:
			// cbMember.getValue().getBuildings().add(new
			// Hdv(cbLevel.getValue()));
			// never add an HDV building, Hdv is managed separately in Player
			LOGGER.info("HDV are not added through this method --");
                break;
            default:
                System.err.println("Unhandled building type : " + cbBuilding.getValue());

        }

    }

    @FXML
    private void rmBuilding(ActionEvent event) {
		// get the selected Player
		Player player = cbMember.getValue();
		// get the selected buildings
		buildingTable.getSelectionModel().getSelectedItems().stream().forEach(p -> player.getBuildings().remove(p));

    }
}
