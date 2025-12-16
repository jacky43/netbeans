package Vue;

import Domaine.DTO.*;
import Domaine.Entite.ElementSelectionnable;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.HeatMyFloorController;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@SuppressWarnings("serial")
public class MainWindow extends javax.swing.JFrame {

    private static final Set<String> MEUBLES_AVEC_DRAIN = Set.of(
            "BAIN", "DOUCHE", "TOILETTE", "VANITE"
    );

    private static final String[] TOUS_LES_MEUBLES = {
        "ARMOIRE", "BAIN", "DOUCHE", "PLACARD", "TOILETTE", "VANITE"
    };

    private javax.swing.JPanel InformationZoneBottomPanel;
    private javax.swing.JMenu aideMenu;
    private javax.swing.JPanel buttonTopPanel;
    private javax.swing.JPanel choixRightPanel;
    private javax.swing.JPanel choixLeftPanel;
    private javax.swing.JPanel drawingCenterPanel;
    private javax.swing.JMenu editionMenu;
    private javax.swing.JMenuItem exporterItem;
    private javax.swing.JMenu fichierMenu;
    private javax.swing.JMenuItem importerItem;
    private boolean isDragging = false;
    private Point dragStartPoint = null;
    private Point elementStartPosition = null;

    // Mode translation de la membrane
    private boolean modeTranslationActive = false;
    private boolean isDraggingIntersection = false;
    private Point intersectionOriginale = null;
    private Point intersectionDragStart = null;

    // LARGEUR ELEMENT SELECTIONNE
    private javax.swing.JLabel largeurJLabel;
    private javax.swing.JTextField largeurPiedJText, largeurPouceJText, largeurFractionJText, largeurElementNumJText, largeurElementDenJText;
    private javax.swing.JLabel LargeurPouceJLabel, LargeurPiedsJLabel;

    // LONGUEUR ELEMENT SELECTIONNE
    private javax.swing.JTextField longueurPiedJText, longueurPouceJText, longueurFractionJText, longueurElementNumJText, longueurElementDenJText;
    private javax.swing.JLabel longueurJLabel;
    private javax.swing.JLabel LongueurPouceJLabel, LongueurPiedsJLabel;

    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> modelisationTypesBox;
    private javax.swing.JMenu nouvelleModelisationMenu;
    private javax.swing.JMenu outilsMenu1;
    private javax.swing.JMenuItem pieceIrreguliereItem;
    private javax.swing.JMenuItem pieceReguliereItem;

    // POSITION X ELEMENT SELECTIONNE
    private javax.swing.JLabel positionXJLabel, positionPiedXJLabel, positionPouceXJLabel;
    private javax.swing.JTextField positionPiedXJText, positionPouceXJText, positionXElementNumJText, positionXElementDenJText;

    // POSITION Y ELEMENT SELECTIONNE
    private javax.swing.JLabel positionYJLabel;
    private javax.swing.JTextField positionPiedYJText, positionPouceYJText, positionYElementNumJText, positionYElementDenJText;

    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem sauvegarderItem;
    private javax.swing.JButton ajoutMeubleSDButton;
    private javax.swing.JButton ajoutElementChauffantButton;
    private javax.swing.JButton ajoutThermostatButton;
    private javax.swing.JButton activerMembraneButton;
    private javax.swing.JButton tracerFilButton;
    private javax.swing.JButton supprimerMeubleButton;
    private javax.swing.JButton modifierMeubleButton;
    private javax.swing.JButton translationMembraneButton;
    private javax.swing.JButton undoButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JButton zoomResetButton;
    private javax.swing.JLabel zoomLabel;
    private javax.swing.JComboBox<String> meubleSansDrainBox;
    private javax.swing.JLabel diametreJLabel;

    // DRAIN
    private javax.swing.JTextField diametrePiedJText, diametrePouceJText, diametreNumJText, diametreDenJText;

    // NOUVEAUX CHAMPS POUR LA POSITION DU DRAIN
    private javax.swing.JLabel positionDrainXJLabel, positionDrainYJLabel;

    // POSITION DRAIN X
    private javax.swing.JTextField positionDrainPiedXJText, positionDrainPouceXJText, positionDrainXNumJText, positionDrainXDenJText;
    // POSITION DRAIN Y
    private javax.swing.JTextField positionDrainPiedYJText, positionDrainPouceYJText, positionDrainYNumJText, positionDrainYDenJText;

    private javax.swing.JTextField largeurPiecePiedJText;
    private javax.swing.JTextField largeurPiecePouceJText;
    private javax.swing.JTextField largeurPieceNumJText;
    private javax.swing.JTextField largeurPieceDenJText;
    private javax.swing.JTextField longueurPiecePiedJText;
    private javax.swing.JTextField longueurPiecePouceJText;
    private javax.swing.JTextField longueurPieceNumJText;
    private javax.swing.JTextField longueurPieceDenJText;
    private javax.swing.JLabel longueurPieceJLabel;
    private javax.swing.JLabel largeurPieceJLabel;
    private javax.swing.JLabel titrePieceJLabel;
    private javax.swing.JButton nouvellePieceJButton;
    private javax.swing.JLabel typePieceJLabel;
    private javax.swing.JComboBox<String> typePieceComboBox;

    // REDIM
    private javax.swing.JLabel redimensionnementPieceTitreLabel;
    private javax.swing.JLabel redimXLabel;
    private javax.swing.JLabel redimYLabel;
    private javax.swing.JTextField redimXJText;
    private javax.swing.JTextField redimYJText;
    private javax.swing.JButton redimPieceJButton;

    // FIL
    private javax.swing.JLabel filTitreLabel;
    private javax.swing.JLabel filLongueurMaxLabel;
    private javax.swing.JLabel filLongueurSegmentMaxLabel;
    private javax.swing.JTextField filLongueurMaxJText;
    private javax.swing.JTextField filLongueurSegmentMaxJText;

    // Position point
    private javax.swing.JLabel CoordonneesTitreJLabel;
    private javax.swing.JLabel CoordonneesPositionXTitreJLabel;
    private javax.swing.JLabel CoordonneesPositionYTitreJLabel;
    private javax.swing.JTextField CoordonneesPosXPiedsJText;
    private javax.swing.JTextField CoordonneesPosXPoucesJText;
    private javax.swing.JTextField CoordonneesPosXNumJText;
    private javax.swing.JTextField CoordonneesPosXDenJText;
    private javax.swing.JTextField CoordonneesPosYPiedsJText;
    private javax.swing.JTextField CoordonneesPosYPoucesJText;
    private javax.swing.JTextField CoordonneesPosYNumJText;
    private javax.swing.JTextField CoordonneesPosYDenJText;
    
    private javax.swing.JLabel ErreurJLabel;

    private final int DIAMETRE_DRAIN_POUCES = 3;
    private final int LONGUEUR_INITIALE_MEUBLE_POUCES = 36;
    private final int LARGEUR_INITIALE_MEUBLE_POUCES = 36;
    private final int LONGUEUR_INITIALE_ELTCHAUFFANT_POUCES = 24;
    private final int LARGEUR_INITIALE_ELTCHAUFFANT_POUCES = 24;
    private final int DPI = 6;
    private final int FACTEUR_CONVERSION_FEET_INCHES = 12;
    private final int DIMENSION_DEFAUT_PIECE_FEET = 10;
    private static final int ESPACEMENT_MENBRANE_POUCES = 3;
    private static final double POUCES_PAR_METRE = 39.3701;
    private static final int DISTANCE_SECURITE_METRES = 3;
    private static final int MARGE_MENBRANE_POUCES = (int) Math.round(DISTANCE_SECURITE_METRES * POUCES_PAR_METRE);

    private double zoomFactor = 1.0;
    private final double ZOOM_INCREMENT = 0.1;
    private double panOffsetX = 0;
    private double panOffsetY = 0;
    private Point lastMousePosition = null;
    private SelectionInfoDTO selectionActuelle = null;

    public HeatMyFloorController controller;
    public DrawingPanel drawingPanel;

    public MainWindow() {
        controller = new HeatMyFloorController();
        drawingPanel = new DrawingPanel(this);
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(screenWidth, screenHeight);
        setResizable(true);
        initComponents();
    }

    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        buttonTopPanel = new javax.swing.JPanel();
        modelisationTypesBox = new javax.swing.JComboBox<>();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        InformationZoneBottomPanel = new javax.swing.JPanel();
        choixLeftPanel = new javax.swing.JPanel();
        drawingCenterPanel = new javax.swing.JPanel();
        choixRightPanel = new javax.swing.JPanel();
        mainMenuBar = new javax.swing.JMenuBar();
        fichierMenu = new javax.swing.JMenu();
        nouvelleModelisationMenu = new javax.swing.JMenu();
        pieceReguliereItem = new javax.swing.JMenuItem();
        pieceIrreguliereItem = new javax.swing.JMenuItem();
        sauvegarderItem = new javax.swing.JMenuItem();
        exporterItem = new javax.swing.JMenuItem();
        importerItem = new javax.swing.JMenuItem();
        editionMenu = new javax.swing.JMenu();
        outilsMenu1 = new javax.swing.JMenu();
        aideMenu = new javax.swing.JMenu();
        ajoutMeubleSDButton = new javax.swing.JButton();
        ajoutElementChauffantButton = new javax.swing.JButton();
        ajoutThermostatButton = new javax.swing.JButton();
        activerMembraneButton = new javax.swing.JButton();
        tracerFilButton = new javax.swing.JButton();
        translationMembraneButton = new javax.swing.JButton();
        supprimerMeubleButton = new javax.swing.JButton();
        modifierMeubleButton = new javax.swing.JButton();
        longueurPiedJText = new javax.swing.JTextField();
        longueurPouceJText = new javax.swing.JTextField();
        longueurFractionJText = new javax.swing.JTextField();
        largeurPiedJText = new javax.swing.JTextField();
        largeurPouceJText = new javax.swing.JTextField();
        largeurFractionJText = new javax.swing.JTextField();
        positionPiedXJText = new javax.swing.JTextField();
        positionPouceXJText = new javax.swing.JTextField();
        positionPiedYJText = new javax.swing.JTextField();
        positionPouceYJText = new javax.swing.JTextField();
        longueurJLabel = new javax.swing.JLabel();
        LongueurPouceJLabel = new javax.swing.JLabel();
        LargeurPouceJLabel = new javax.swing.JLabel();
        largeurJLabel = new javax.swing.JLabel();
        LongueurPiedsJLabel = new javax.swing.JLabel();
        LargeurPiedsJLabel = new javax.swing.JLabel();
        positionXJLabel = new javax.swing.JLabel();
        positionYJLabel = new javax.swing.JLabel();
        meubleSansDrainBox = new javax.swing.JComboBox<>();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        zoomResetButton = new javax.swing.JButton();
        zoomLabel = new javax.swing.JLabel();
        diametrePiedJText = new javax.swing.JTextField();
        diametreJLabel = new javax.swing.JLabel();

        // INITIALISATION DES NOUVEAUX CHAMPS POUR LE DRAIN
        positionDrainXJLabel = new javax.swing.JLabel();
        positionDrainPiedXJText = new javax.swing.JTextField();
        positionDrainYJLabel = new javax.swing.JLabel();
        positionDrainPiedYJText = new javax.swing.JTextField();

        longueurPiecePiedJText = new javax.swing.JTextField(3);
        longueurPiecePouceJText = new javax.swing.JTextField(3);
        longueurPieceNumJText = new javax.swing.JTextField(3);
        longueurPieceDenJText = new javax.swing.JTextField(3);

        largeurPiecePiedJText = new javax.swing.JTextField(3); //  FIX: plus de doublon
        largeurPiecePouceJText = new javax.swing.JTextField(3);
        largeurPieceNumJText = new javax.swing.JTextField(3);
        largeurPieceDenJText = new javax.swing.JTextField(3);

        longueurPieceJLabel = new javax.swing.JLabel();
        largeurPieceJLabel = new javax.swing.JLabel();
        titrePieceJLabel = new javax.swing.JLabel();
        nouvellePieceJButton = new javax.swing.JButton();
        typePieceJLabel = new javax.swing.JLabel();
        typePieceComboBox = new javax.swing.JComboBox<>();

        ErreurJLabel = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.BorderLayout());

        buttonTopPanel.setPreferredSize(new java.awt.Dimension(1000, 50));

        modelisationTypesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Modélisation Pièce", "Modélisation Fil"}));
        buttonTopPanel.add(modelisationTypesBox);

        meubleSansDrainBox.setModel(new javax.swing.DefaultComboBoxModel<>(TOUS_LES_MEUBLES));
        buttonTopPanel.add(meubleSansDrainBox);

        undoButton.setText("Undo");
        undoButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            undoButtonActionPerformed(evt);
        });
        buttonTopPanel.add(undoButton);

        redoButton.setText("Redo");
        redoButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            redoButtonActionPerformed(evt);
        });
        buttonTopPanel.add(redoButton);

        zoomInButton.setText("Zoom +");
        zoomInButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            zoomInButtonActionPerformed(evt);
        });
        buttonTopPanel.add(zoomInButton);

        zoomOutButton.setText("Zoom -");
        zoomOutButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            zoomOutButtonActionPerformed(evt);
        });
        buttonTopPanel.add(zoomOutButton);

        zoomResetButton.setText("Zoom 100%");
        zoomResetButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            zoomResetButtonActionPerformed(evt);
        });
        buttonTopPanel.add(zoomResetButton);

        zoomLabel.setText("100%");
        buttonTopPanel.add(zoomLabel);

        ajoutMeubleSDButton.setText("Ajouter Meuble");
        ajoutMeubleSDButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            ajoutMeubleSDButtonActionPerformed(evt);
        });
        buttonTopPanel.add(ajoutMeubleSDButton);

        ajoutElementChauffantButton.setText("Ajouter Element Chauffant");
        ajoutElementChauffantButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            ajoutElementChauffantButtonActionPerformed(evt);
        });
        buttonTopPanel.add(ajoutElementChauffantButton);

        ajoutThermostatButton.setText("Ajouter Thermostat");
        ajoutThermostatButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            ajouterThermostat();
        });
        buttonTopPanel.add(ajoutThermostatButton);

        activerMembraneButton.setText("Activer Membrane");
        activerMembraneButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            activerMembrane();
        });

        tracerFilButton.setText("Tracer Fil Chauffant");
        tracerFilButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            tracerFilChauffant();
        });

        translationMembraneButton.setText("Translation Membrane");
        translationMembraneButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            //toggleModeTranslation();
        });

        supprimerMeubleButton.setText("Supprimer");
        supprimerMeubleButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            supprimerMeubleButtonActionPerformed(evt);
        });
        buttonTopPanel.add(supprimerMeubleButton);

        modifierMeubleButton.setText("Modifier élément sélectionné");
        modifierMeubleButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            modifierElementButtonActionPerformed(evt);
        });
        buttonTopPanel.add(modifierMeubleButton);

        diametrePiedJText.setPreferredSize(new java.awt.Dimension(100, 22));
        diametreJLabel.setText("Diamètre drain");

        mainPanel.add(buttonTopPanel, java.awt.BorderLayout.NORTH);

        InformationZoneBottomPanel.setPreferredSize(new java.awt.Dimension(1000, 100));
        javax.swing.GroupLayout InformationZoneBottomPanelLayout = new javax.swing.GroupLayout(InformationZoneBottomPanel);
        InformationZoneBottomPanel.setLayout(InformationZoneBottomPanelLayout);

        InformationZoneBottomPanelLayout.setHorizontalGroup(
                InformationZoneBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(InformationZoneBottomPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ErreurJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                                .addContainerGap()
                        )
        );
        InformationZoneBottomPanelLayout.setVerticalGroup(
                InformationZoneBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(InformationZoneBottomPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ErreurJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                .addContainerGap()
                        )
        );

        mainPanel.add(InformationZoneBottomPanel, java.awt.BorderLayout.SOUTH);

        drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!isDragging) {
                    drawingCenterPanelMouseClicked(e);
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                drawingPanelMousePressed(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                drawingPanelMouseReleased(e);
            }
        });

        drawingPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                lastMousePosition = e.getPoint();

                // Convert screen coordinates to world coordinates
                Point worldPos = screenToWorld(e.getPoint());

                // Update coordinate display fields
                afficherCoordonnee(worldPos.x,
                        CoordonneesPosXPiedsJText,
                        CoordonneesPosXPoucesJText,
                        CoordonneesPosXNumJText,
                        CoordonneesPosXDenJText);

                afficherCoordonnee(worldPos.y,
                        CoordonneesPosYPiedsJText,
                        CoordonneesPosYPoucesJText,
                        CoordonneesPosYNumJText,
                        CoordonneesPosYDenJText);
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                drawingPanelMouseDragged(e);
            }
        });

        drawingPanel.addMouseWheelListener((java.awt.event.MouseWheelEvent evt) -> {
            drawingPanelMouseWheelMoved(evt);
        });

        choixLeftPanel = new javax.swing.JPanel();
        choixLeftPanel.setPreferredSize(new java.awt.Dimension(200, 350));
        choixLeftPanel.setBackground(java.awt.Color.WHITE);
        choixLeftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Élément sélectionné"));

        // Initialize fields ONCE
        longueurPiedJText = new javax.swing.JTextField(3);
        longueurPouceJText = new javax.swing.JTextField(3);
        longueurElementNumJText = new javax.swing.JTextField(3);
        longueurElementDenJText = new javax.swing.JTextField(3);

        largeurPiedJText = new javax.swing.JTextField(3);
        largeurPouceJText = new javax.swing.JTextField(3);
        largeurElementNumJText = new javax.swing.JTextField(3);
        largeurElementDenJText = new javax.swing.JTextField(3);

        positionPiedXJText = new javax.swing.JTextField(3);
        positionPouceXJText = new javax.swing.JTextField(3);
        positionXElementNumJText = new javax.swing.JTextField(3);
        positionXElementDenJText = new javax.swing.JTextField(3);

        positionPiedYJText = new javax.swing.JTextField(3);
        positionPouceYJText = new javax.swing.JTextField(3);
        positionYElementNumJText = new javax.swing.JTextField(3);
        positionYElementDenJText = new javax.swing.JTextField(3);

        diametrePiedJText = new javax.swing.JTextField(3);
        diametrePouceJText = new javax.swing.JTextField(3);
        diametreNumJText = new javax.swing.JTextField(3);
        diametreDenJText = new javax.swing.JTextField(3);

        // Drain position fields
        positionDrainPiedXJText = new javax.swing.JTextField(3);
        positionDrainPouceXJText = new javax.swing.JTextField(3);
        positionDrainXNumJText = new javax.swing.JTextField(3);
        positionDrainXDenJText = new javax.swing.JTextField(3);

        positionDrainPiedYJText = new javax.swing.JTextField(3);
        positionDrainPouceYJText = new javax.swing.JTextField(3);
        positionDrainYNumJText = new javax.swing.JTextField(3);
        positionDrainYDenJText = new javax.swing.JTextField(3);

        javax.swing.GroupLayout choixLeftPanelLayout = new javax.swing.GroupLayout(choixLeftPanel);
        choixLeftPanel.setLayout(choixLeftPanelLayout);

        javax.swing.JLabel longueurLabel = new javax.swing.JLabel("Longueur (X' Y'' Z/W)");
        javax.swing.JLabel largeurLabel = new javax.swing.JLabel("Largeur (X' Y'' Z/W)");
        javax.swing.JLabel posXLabel = new javax.swing.JLabel("Position X (X' Y'' Z/W)");
        javax.swing.JLabel posYLabel = new javax.swing.JLabel("Position Y (X' Y'' Z/W)");
        javax.swing.JLabel diametreLabel = new javax.swing.JLabel("Diamètre Drain (X' Y'' Z/W)");
        javax.swing.JLabel positionDrainXLabel = new javax.swing.JLabel("Position X Drain (X' Y'' Z/W)");
        javax.swing.JLabel positionDrainYLabel = new javax.swing.JLabel("Position Y Drain (X' Y'' Z/W)");

        filTitreLabel = new JLabel("Modélisation FIL");
        filLongueurMaxLabel = new JLabel("Longueur du fil (en pieds)");
        filLongueurSegmentMaxLabel = new JLabel("Longueur segment du fil (en pieds)");
        filLongueurMaxJText = new JTextField();
        filLongueurSegmentMaxJText = new JTextField();
        CoordonneesTitreJLabel = new JLabel("Coordonnées position : ");
        CoordonneesPositionXTitreJLabel = new JLabel("Position X");
        CoordonneesPositionYTitreJLabel = new JLabel("Position Y");
        CoordonneesPosXPiedsJText = new JTextField();
        CoordonneesPosXPoucesJText = new JTextField();
        CoordonneesPosXNumJText = new JTextField();
        CoordonneesPosXDenJText = new JTextField();
        CoordonneesPosYPiedsJText = new JTextField();
        CoordonneesPosYPoucesJText = new JTextField();
        CoordonneesPosYNumJText = new JTextField();
        CoordonneesPosYDenJText = new JTextField();

        // Make coordinate display fields non-editable
        CoordonneesPosXPiedsJText.setEditable(false);
        CoordonneesPosXPoucesJText.setEditable(false);
        CoordonneesPosXNumJText.setEditable(false);
        CoordonneesPosXDenJText.setEditable(false);
        CoordonneesPosYPiedsJText.setEditable(false);
        CoordonneesPosYPoucesJText.setEditable(false);
        CoordonneesPosYNumJText.setEditable(false);
        CoordonneesPosYDenJText.setEditable(false);

// Optional: Set background color to indicate read-only
        java.awt.Color readOnlyColor = new java.awt.Color(240, 240, 240);
        CoordonneesPosXPiedsJText.setBackground(readOnlyColor);
        CoordonneesPosXPoucesJText.setBackground(readOnlyColor);
        CoordonneesPosXNumJText.setBackground(readOnlyColor);
        CoordonneesPosXDenJText.setBackground(readOnlyColor);
        CoordonneesPosYPiedsJText.setBackground(readOnlyColor);
        CoordonneesPosYPoucesJText.setBackground(readOnlyColor);
        CoordonneesPosYNumJText.setBackground(readOnlyColor);
        CoordonneesPosYDenJText.setBackground(readOnlyColor);

        redimensionnementPieceTitreLabel = new JLabel("Redimensionnement Pièce");
        redimXLabel = new JLabel("X : ");
        redimYLabel = new JLabel("Y : ");
        redimXJText = new JTextField();
        redimYJText = new JTextField();
        redimPieceJButton = new JButton("Redimensionner");

choixLeftPanelLayout.setHorizontalGroup(
        choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(longueurLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(longueurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(longueurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(longueurElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(longueurElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(largeurLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(largeurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(largeurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(largeurElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(largeurElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(posXLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(positionPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionXElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionXElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addComponent(posYLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(positionPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionYElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionYElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(diametreLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(diametrePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(diametrePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(diametreNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(diametreDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(positionDrainXLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(positionDrainPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(positionDrainYLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(positionDrainPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(positionDrainYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                                // Coordonnées de la souris
                                .addComponent(CoordonneesTitreJLabel)
                                .addComponent(CoordonneesPositionXTitreJLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(CoordonneesPosXPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosXPoucesJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                                .addComponent(CoordonneesPositionYTitreJLabel)
                                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                        .addComponent(CoordonneesPosYPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosYPoucesJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CoordonneesPosYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
);

choixLeftPanelLayout.setVerticalGroup(
        choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(longueurLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(longueurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(longueurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(longueurElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(longueurElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(largeurLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(largeurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(largeurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(largeurElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(largeurElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(posXLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(positionPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionXElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionXElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(posYLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(positionPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionYElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionYElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(diametreLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(diametrePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diametrePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diametreNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diametreDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(positionDrainXLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(positionDrainPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12)
                        .addComponent(positionDrainYLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(positionDrainPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(positionDrainYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))

                        .addGap(12, 12, 12)

                        .addComponent(CoordonneesTitreJLabel)
                        .addComponent(CoordonneesPositionXTitreJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CoordonneesPosXPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosXPoucesJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))

                        .addGap(12, 12, 12)
                        .addComponent(CoordonneesPositionYTitreJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CoordonneesPosYPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosYPoucesJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CoordonneesPosYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
);
        mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST);

        drawingCenterPanel = new javax.swing.JPanel();
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(220, 350));
        drawingCenterPanel.setLayout(new java.awt.BorderLayout());
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);

        choixRightPanel.setPreferredSize(new java.awt.Dimension(200, 350));
        choixRightPanel.setBackground(java.awt.Color.WHITE);
        choixRightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));

        longueurPieceJLabel.setText("Longueur");
        largeurPieceJLabel.setText("Largeur");
        titrePieceJLabel.setText("Pièce ");
        nouvellePieceJButton.setText("Nouvelle piece");
        nouvellePieceJButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            nouvellePieceButtonActionPerformed(evt);
        });
        typePieceJLabel.setText("Type");
        typePieceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Regulière", "Irrégulière"}));

        javax.swing.GroupLayout choixRightPanelLayout = new javax.swing.GroupLayout(choixRightPanel);
        choixRightPanel.setLayout(choixRightPanelLayout);

        javax.swing.JLabel pieceTitleLabel = new javax.swing.JLabel("Pièce");
        javax.swing.JLabel typeLabel = new javax.swing.JLabel("Type");
        javax.swing.JLabel longueurPieceLabel = new javax.swing.JLabel("Longueur (X' Y'' Z/W)");
        javax.swing.JLabel largeurPieceLabel = new javax.swing.JLabel("Largeur (X' Y'' Z/W)");

        choixRightPanelLayout.setHorizontalGroup(
                choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pieceTitleLabel)
                                        .addComponent(typeLabel)
                                        .addComponent(typePieceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longueurPieceLabel)
                                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                                .addComponent(longueurPiecePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(longueurPiecePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(longueurPieceNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(longueurPieceDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(largeurPieceLabel)
                                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                                .addComponent(largeurPiecePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(largeurPiecePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(largeurPieceNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(largeurPieceDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(nouvellePieceJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(redimensionnementPieceTitreLabel)
                                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                            .addComponent(redimXLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(redimXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(redimYLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(redimYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(redimPieceJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(filTitreLabel)
                                        .addComponent(filLongueurMaxLabel)
                                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                                .addComponent(filLongueurMaxJText, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(filLongueurSegmentMaxLabel)
                                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                                .addComponent(filLongueurSegmentMaxJText, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        )
                                        // IMPORTANT : ces boutons doivent être dans HORIZONTAL + VERTICAL
                                        .addComponent(tracerFilButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(activerMembraneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(translationMembraneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        choixRightPanelLayout.setVerticalGroup(
                choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(choixRightPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pieceTitleLabel)
                                .addGap(20)
                                .addComponent(typeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(typePieceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(longueurPieceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(longueurPiecePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longueurPiecePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longueurPieceNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longueurPieceDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addGap(20)
                                .addComponent(largeurPieceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(largeurPiecePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(largeurPiecePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(largeurPieceNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(largeurPieceDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addGap(20)
                                .addComponent(nouvellePieceJButton)
                                .addGap(10)
                                .addComponent(redimensionnementPieceTitreLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(redimXLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(redimXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(redimYLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(redimYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addGap(15)
                                .addComponent(redimPieceJButton)
                                // Fil
                                .addGap(20)
                                .addComponent(filTitreLabel)

                                .addGap(20)
                                .addComponent(filLongueurMaxLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(filLongueurMaxJText)
                                )

                                .addGap(10)
                                .addComponent(filLongueurSegmentMaxLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(filLongueurSegmentMaxJText)
                                )
                                // AJOUT OBLIGATOIRE DANS LE VERTICAL
                                .addGap(20)
                                .addComponent(tracerFilButton)
                                .addGap(20)
                                .addComponent(activerMembraneButton)
                                .addGap(20)
                                .addComponent(translationMembraneButton)
                                .addContainerGap(60, Short.MAX_VALUE)
                        )
        );

        mainPanel.add(choixRightPanel, java.awt.BorderLayout.EAST);

        fichierMenu.setText("Fichier");

        sauvegarderItem.setText("Sauvegarder");
        fichierMenu.add(sauvegarderItem);

        sauvegarderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SauvegarderFichier();
            }
        });

        exporterItem.setText("Exporter");
        fichierMenu.add(exporterItem);
        exporterItem.addActionListener(e -> exporterPNG()); // Export PNG 

        importerItem.setText("Importer");
        fichierMenu.add(importerItem);

        importerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImporterFichier();
            }
        });

        mainMenuBar.add(fichierMenu);

        editionMenu.setText("Édition");
        mainMenuBar.add(editionMenu);

        outilsMenu1.setText("Outils");
        mainMenuBar.add(outilsMenu1);

        aideMenu.setText("Aide");
        mainMenuBar.add(aideMenu);

        setJMenuBar(mainMenuBar);

        drawingCenterPanel.setBackground(java.awt.Color.BLACK);

        javax.swing.GroupLayout layout2 = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout2);
        layout2.setHorizontalGroup(
                layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout2.setVerticalGroup(
                layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    // EXPORT PNG
    private void exporterPNG() {
        if (drawingPanel == null) {
            afficherErreur("Aucun panneau de dessin à exporter.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exporter en PNG");
        chooser.setFileFilter(new FileNameExtensionFilter("Image PNG (*.png)", "png"));
        chooser.setSelectedFile(new File("export.png"));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".png")) {
            file = new File(path + ".png");
        }

        try {
            int w = drawingPanel.getWidth();
            int h = drawingPanel.getHeight();
            if (w <= 0 || h <= 0) {
                afficherErreur("Taille invalide du panneau.");
                return;
            }

            int scale = 2; // meilleure qualité
            BufferedImage image = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.scale(scale, scale);
            drawingPanel.printAll(g2);
            g2.dispose();

            ImageIO.write(image, "png", file);
            afficherErreur("Export PNG réussi : " + file.getAbsolutePath());
        } catch (IOException ex) {
            afficherErreur("Erreur export PNG : " + ex.getMessage());
        }
    }

    // Helper to build a row: label + 4 fields
    JPanel row(String label, JTextField ft, JTextField in, JTextField num, JTextField den) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 2));
        p.add(new JLabel(label));
        p.add(new JLabel("ft"));
        p.add(ft);
        p.add(new JLabel("in"));
        p.add(in);
        p.add(new JLabel("  "));
        p.add(num);
        p.add(new JLabel("/"));
        p.add(den);
        p.setBorder(BorderFactory.createTitledBorder(""));
        return p;
    }

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
        zoomAt(center, ZOOM_INCREMENT);
    }

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
        zoomAt(center, -ZOOM_INCREMENT);
    }

    private void zoomResetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        zoomFactor = 1.0;
        panOffsetX = 0;
        panOffsetY = 0;
        updateZoom();
    }

    private void zoomAt(Point mousePos, double delta) {
        double oldZoom = zoomFactor;
        double newZoom = zoomFactor + delta;

        if (newZoom < 0.0) {
            newZoom = 1e-10;
        }

        double worldX = (mousePos.x - panOffsetX) / oldZoom;
        double worldY = (mousePos.y - panOffsetY) / oldZoom;

        zoomFactor = newZoom;
        panOffsetX = mousePos.x - (worldX * zoomFactor);
        panOffsetY = mousePos.y - (worldY * zoomFactor);

        updateZoom();
    }

    private void updateZoom() {
        zoomLabel.setText(String.format("%.0f%%", zoomFactor * 100));
        if (drawingPanel != null) {
            drawingPanel.setZoomFactor(zoomFactor);
            drawingPanel.setPanOffset(panOffsetX, panOffsetY);
        }
        rafraichirVue();
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public double getPanOffsetX() {
        return panOffsetX;
    }

    public double getPanOffsetY() {
        return panOffsetY;
    }

    private void drawingCenterPanelMouseClicked(java.awt.event.MouseEvent evt) {
        Point positionSouris = evt.getPoint();
        Point positionMonde = screenToWorld(positionSouris);

        Object selection = controller.SelectionnerElementAvecType(positionMonde).getElement();
        if (selection instanceof ElementSelectionnableDTO elementDTO) {
            mettreAJourPanneauSelection(elementDTO);
        } else {
            reinitialiserPanneauEdition();
        }
        rafraichirVue();
    }

    private void drawingPanelMousePressed(java.awt.event.MouseEvent e) {
        Point positionSouris = e.getPoint();
        Point positionMonde = screenToWorld(positionSouris);

        selectionActuelle = controller.SelectionnerElementAvecType(positionMonde);

        if (selectionActuelle.aSelection()) {
            controller.saveStateBeforeDrag();
            isDragging = true;
            dragStartPoint = positionSouris;

            if (selectionActuelle.estDrain()) {
                elementStartPosition = selectionActuelle.getPointClique();
                drawingPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
            } else {
                elementStartPosition = selectionActuelle.getElement().getPosition();
                drawingPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.MOVE_CURSOR));
            }
        }

        rafraichirVue();
    }

    private void drawingPanelMouseDragged(java.awt.event.MouseEvent e) {
        if (!isDragging || dragStartPoint == null || elementStartPosition == null || selectionActuelle == null) {
            return;
        }

        Point positionActuelle = e.getPoint();
        int deltaX = positionActuelle.x - dragStartPoint.x;
        int deltaY = positionActuelle.y - dragStartPoint.y;

        int deltaXMonde = (int) (deltaX / (zoomFactor * DPI));
        int deltaYMonde = -(int) (deltaY / (zoomFactor * DPI));

        if (selectionActuelle.estDrain()) {
            // Drag drain
            Point nouveauCentreDrain = new Point(
                    elementStartPosition.x + deltaXMonde,
                    elementStartPosition.y + deltaYMonde
            );
            controller.ModifierPositionDrain(nouveauCentreDrain);
        } else {
            // Drag element body
            Point nouvellePosition = new Point(
                    elementStartPosition.x + deltaXMonde,
                    elementStartPosition.y + deltaYMonde
            );

            ElementSelectionnableDTO element = selectionActuelle.getElement();
            controller.ModifierElementSelectionne(
                    nouvellePosition,
                    element.getLargeur(),
                    element.getLongueur()
            );
        }

        rafraichirVue();
    }

    private void drawingPanelMouseReleased(java.awt.event.MouseEvent e) {
        if (isDraggingIntersection) {
            isDraggingIntersection = false;
            intersectionOriginale = null;
            intersectionDragStart = null;
            drawingPanel.setCursor(java.awt.Cursor.getDefaultCursor());
            rafraichirVue();
            return;
        }

        if (isDragging) {
            isDragging = false;
            dragStartPoint = null;
            elementStartPosition = null;
            selectionActuelle = null;

            drawingPanel.setCursor(java.awt.Cursor.getDefaultCursor());
            controller.saveStateAfterDrag();

            Object elementSelectionne = controller.ObtenirElementSelectionne();
            if (elementSelectionne instanceof ElementSelectionnableDTO) {
                mettreAJourPanneauSelection((ElementSelectionnableDTO) elementSelectionne);
            }
            rafraichirVue();
        }
    }

    private void drawingPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
        int rotation = evt.getWheelRotation();
        Point mousePos = evt.getPoint();

        if (rotation < 0) {
            zoomAt(mousePos, ZOOM_INCREMENT);
        } else {
            zoomAt(mousePos, -ZOOM_INCREMENT);
        }
    }

    private Point screenToWorld(Point screenPos) {
        double adjustedX = (screenPos.x - panOffsetX) / zoomFactor;
        double adjustedY = (screenPos.y - panOffsetY) / zoomFactor;

        Point origine = drawingPanel.getOrigineAxes();
        int worldX = (int) (adjustedX / DPI) - origine.x;
        int worldY = origine.y - (int) (adjustedY / DPI);

        return new Point(worldX, worldY);
    }

    private Point worldToScreen(Point worldPos) {
        Point origine = drawingPanel.getOrigineAxes();

        double panelX = (worldPos.x + origine.x) * DPI;
        double panelY = (origine.y - worldPos.y) * DPI;

        int screenX = (int) (panelX * zoomFactor + panOffsetX);
        int screenY = (int) (panelY * zoomFactor + panOffsetY);

        return new Point(screenX, screenY);
    }

    private void ajouterMeuble(String typeMeuble) {
        if (typeMeuble == null || typeMeuble.isBlank()) {
            typeMeuble = "MEUBLE";
        }

        Point positionMeuble = new Point(0, LONGUEUR_INITIALE_MEUBLE_POUCES);
        MeubleDTO dto;

        if (MEUBLES_AVEC_DRAIN.contains(typeMeuble)) {
            Point centreDrain = new Point(LARGEUR_INITIALE_MEUBLE_POUCES / 2, LONGUEUR_INITIALE_MEUBLE_POUCES / 2);
            dto = new MeubleDTO(positionMeuble, LONGUEUR_INITIALE_MEUBLE_POUCES, LARGEUR_INITIALE_MEUBLE_POUCES, typeMeuble, centreDrain, DIAMETRE_DRAIN_POUCES);
        } else {
            dto = new MeubleDTO(positionMeuble, LONGUEUR_INITIALE_MEUBLE_POUCES, LARGEUR_INITIALE_MEUBLE_POUCES, typeMeuble);
        }

        controller.AjouterMeuble(dto);
        Object selection = controller.SelectionnerElementAvecType(positionMeuble).getElement();
        if (selection instanceof ElementSelectionnableDTO elementDTO) {
            mettreAJourPanneauSelection(elementDTO);
        }
        rafraichirVue();
    }

    private void ajoutMeubleSDButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String typeMeuble = (String) meubleSansDrainBox.getSelectedItem();
        ajouterMeuble(typeMeuble);
    }

    private void ajoutElementChauffantButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ajouterElementchauffant();
    }

    private void ajouterElementchauffant() {
        Point positionElementChauffant = new Point(0, 0);
        ElementChauffantDTO dto = new ElementChauffantDTO(positionElementChauffant, LONGUEUR_INITIALE_ELTCHAUFFANT_POUCES, LARGEUR_INITIALE_ELTCHAUFFANT_POUCES);
        controller.AjouterElementChauffant(dto);
        rafraichirVue();

        Object elementAjoute = controller.ObtenirElementSelectionne();
        if (elementAjoute == null) {
            Object selection = controller.SelectionnerElementAvecType(positionElementChauffant).getElement();
            if (selection instanceof ElementSelectionnableDTO elementDTO) {
                mettreAJourPanneauSelection(elementDTO);
            }
        }
        rafraichirVue();
    }

    private int convertInchesToPixels(int valeurEnPouces) {
        return (int) Math.round(valeurEnPouces * DPI);
    }

    private int convertPixelsToInches(int valeurEnPixels) {
        return (int) Math.round(valeurEnPixels / DPI);
    }

    private void supprimerMeubleButtonActionPerformed(java.awt.event.ActionEvent e) {
        Object elementSelectionne = controller.ObtenirElementSelectionne();

        if (elementSelectionne == null) {
            afficherErreur("Aucun élément sélectionné.");
            return;
        }

        boolean supprime = controller.SupprimerElementSelectionne();

        if (supprime) {
            reinitialiserPanneauEdition();
            rafraichirVue();
        } else {
            afficherErreur("La suppression a échoué.");
        }
    }

    private void modifierElementButtonActionPerformed(java.awt.event.ActionEvent e) {
        int longueur = 0;
        int largeur = 0;
        int positionX = 0;
        int positionY = 0;

        if (!longueurPouceJText.getText().trim().isEmpty()) {
            try {
                longueur += Integer.parseInt(longueurPouceJText.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
        }

        if (!longueurPiedJText.getText().trim().isEmpty()) {
            try {
                longueur += Integer.parseInt(longueurPiedJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES;
            } catch (NumberFormatException ex) {
                return;
            }
        }

        if (!largeurPouceJText.getText().trim().isEmpty()) {
            try {
                largeur += Integer.parseInt(largeurPouceJText.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
        }

        if (!largeurPiedJText.getText().trim().isEmpty()) {
            try {
                largeur += Integer.parseInt(largeurPiedJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES;
            } catch (NumberFormatException ex) {
                return;
            }
        }

        if (!positionPiedXJText.getText().trim().isEmpty()) {
            try {
                positionX += Integer.parseInt(positionPiedXJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES;
            } catch (NumberFormatException ex) {
                return;
            }
        }
        if (!positionPouceXJText.getText().trim().isEmpty()) {
            try {
                positionX += Integer.parseInt(positionPouceXJText.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
        }

        if (!positionPiedYJText.getText().trim().isEmpty()) {
            try {
                positionY += Integer.parseInt(positionPiedYJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES;
            } catch (NumberFormatException ex) {
                return;
            }
        }
        if (!positionPouceYJText.getText().trim().isEmpty()) {
            try {
                positionY += Integer.parseInt(positionPouceYJText.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
        }

        int longueurConvertie = longueur;
        int largeurConvertie = largeur;
        Point positionConvertie = new Point(positionX, positionY + longueur);

        Object elementSelectionne = controller.ObtenirElementSelectionne();
        MeubleDTO meubleDto = (elementSelectionne instanceof MeubleDTO) ? (MeubleDTO) elementSelectionne : null;

        // DRAIN
        if (meubleDto != null && MEUBLES_AVEC_DRAIN.contains(meubleDto.getNom())) {
            ElementSelectionnable element = controller.ObtenirElementSelectionneDirect();

            if (element instanceof MeubleAvecDrain meubleAvecDrain) {
                String diamText = diametrePiedJText.getText().trim();
                String diamTextPouce = diametrePouceJText.getText().trim();
                if (!diamText.isEmpty() || !diamTextPouce.isEmpty()) {
                    try {
                        int diamInches = 0;
                        if (!diamText.isEmpty()) {
                            diamInches += Integer.parseInt(diamText) * FACTEUR_CONVERSION_FEET_INCHES;
                        }
                        if (!diamTextPouce.isEmpty()) {
                            diamInches += Integer.parseInt(diamTextPouce);
                        }

                        if (diamInches > 0) {
                            meubleAvecDrain.setDiametreDrain(diamInches);
                        }
                    } catch (NumberFormatException ex) {
                        afficherErreur("Diamètre invalide");
                    }
                }

                int drainX = -1;
                int drainY = -1;
                boolean drainPosModifiee = false;

                String drainXPiedText = positionDrainPiedXJText.getText().trim();
                String drainXPouceText = positionDrainPouceXJText.getText().trim();

                if (!drainXPiedText.isEmpty() || !drainXPouceText.isEmpty()) {
                    drainX = 0;
                    if (!drainXPiedText.isEmpty()) {
                        try {
                            drainX += Integer.parseInt(drainXPiedText) * FACTEUR_CONVERSION_FEET_INCHES;
                        } catch (NumberFormatException ex) {
                            afficherErreur("Erreur parse drain X pieds");
                        }
                    }
                    if (!drainXPouceText.isEmpty()) {
                        try {
                            drainX += Integer.parseInt(drainXPouceText);
                        } catch (NumberFormatException ex) {
                            afficherErreur("Erreur parse drain X pouces");
                        }
                    }
                    drainPosModifiee = true;
                }

                String drainYPiedText = positionDrainPiedYJText.getText().trim();
                String drainYPouceText = positionDrainPouceYJText.getText().trim();

                if (!drainYPiedText.isEmpty() || !drainYPouceText.isEmpty()) {
                    drainY = 0;
                    if (!drainYPiedText.isEmpty()) {
                        try {
                            drainY += Integer.parseInt(drainYPiedText) * FACTEUR_CONVERSION_FEET_INCHES;
                        } catch (NumberFormatException ex) {
                            afficherErreur("Erreur parse drain Y pieds");
                        }
                    }
                    if (!drainYPouceText.isEmpty()) {
                        try {
                            drainY += Integer.parseInt(drainYPouceText);
                        } catch (NumberFormatException ex) {
                            afficherErreur("Erreur parse drain Y pouces");
                        }
                    }
                }

                if (drainPosModifiee && drainX >= 0 && drainY >= 0) {
                    if (drainX <= largeurConvertie && drainY <= longueurConvertie) {
                        Point nouveauCentre = new Point(drainX, drainY);
                        meubleAvecDrain.setCentreDrain(nouveauCentre);
                    } else {
                        afficherErreur("Position drain hors limites du meuble");
                    }
                }
            }
        }

        boolean modifie = controller.ModifierElementSelectionne(
                new Point(positionConvertie.x, positionConvertie.y),
                largeurConvertie,
                longueurConvertie);

        if (modifie) {
            Object maj = controller.ObtenirElementSelectionne();
            if (maj instanceof ElementSelectionnableDTO elemetDTO) {
                mettreAJourPanneauSelection(elemetDTO);
            }
            rafraichirVue();
        }
    }

    private void mettreAJourPanneauSelection(ElementSelectionnableDTO element) {
        if (element == null) {
            return;
        }

        int longueur = element.getLongueur();
        int largeur = element.getLargeur();

        longueurPiedJText.setText(Integer.toString((int) longueur / FACTEUR_CONVERSION_FEET_INCHES));
        largeurPiedJText.setText(Integer.toString((int) largeur / FACTEUR_CONVERSION_FEET_INCHES));
        longueurPouceJText.setText(Integer.toString(longueur % FACTEUR_CONVERSION_FEET_INCHES));
        largeurPouceJText.setText(Integer.toString(largeur % FACTEUR_CONVERSION_FEET_INCHES));

        Point positionBase = element.getPosition();
        positionPiedXJText.setText(Integer.toString(positionBase.x / FACTEUR_CONVERSION_FEET_INCHES));
        positionPiedYJText.setText(Integer.toString((positionBase.y - longueur) / FACTEUR_CONVERSION_FEET_INCHES));
        positionPouceXJText.setText(Integer.toString(positionBase.x % FACTEUR_CONVERSION_FEET_INCHES));
        positionPouceYJText.setText(Integer.toString((positionBase.y - longueur) % FACTEUR_CONVERSION_FEET_INCHES));

        if (element instanceof MeubleDTO meuble && meuble.estAvecDrain()) {
            diametrePiedJText.setText(Integer.toString(meuble.getDiametreDrain() / FACTEUR_CONVERSION_FEET_INCHES));
            diametrePouceJText.setText(Integer.toString(meuble.getDiametreDrain() % FACTEUR_CONVERSION_FEET_INCHES));
            diametrePiedJText.setEnabled(true);

            Point centreDrain = meuble.getCentreDrain();
            if (centreDrain != null) {
                positionDrainPiedXJText.setText(Integer.toString((int) (centreDrain.x / FACTEUR_CONVERSION_FEET_INCHES)));
                positionDrainPiedYJText.setText(Integer.toString((int) (centreDrain.y / FACTEUR_CONVERSION_FEET_INCHES)));
                positionDrainPiedXJText.setEnabled(true);
                positionDrainPiedYJText.setEnabled(true);
                positionDrainPouceXJText.setText(Integer.toString((int) (centreDrain.x % FACTEUR_CONVERSION_FEET_INCHES)));
                positionDrainPouceYJText.setText(Integer.toString((int) (centreDrain.y % FACTEUR_CONVERSION_FEET_INCHES)));
                positionDrainPouceXJText.setEnabled(true);
                positionDrainPouceYJText.setEnabled(true);
            } else {
                positionDrainPiedXJText.setText("");
                positionDrainPiedYJText.setText("");
                positionDrainPiedXJText.setEnabled(false);
                positionDrainPiedYJText.setEnabled(false);
            }
        } else {
            diametrePiedJText.setText("");
            diametrePiedJText.setEnabled(false);
            positionDrainPiedXJText.setText("");
            positionDrainPiedYJText.setText("");
            positionDrainPiedXJText.setEnabled(false);
            positionDrainPiedYJText.setEnabled(false);
        }
    }

    private void reinitialiserPanneauEdition() {
        longueurPiedJText.setText("");
        longueurPouceJText.setText("");
        largeurPiedJText.setText("");
        largeurPouceJText.setText("");
        positionPiedXJText.setText("");
        positionPouceXJText.setText("");
        positionPiedYJText.setText("");
        positionPouceYJText.setText("");
        diametrePiedJText.setText("");
        positionDrainPiedXJText.setText("");
        positionDrainPiedYJText.setText("");
    }

    private void afficherErreur(String message) {
        ErreurJLabel.setText("Erreur: " + message);
    }

    private void rafraichirVue() {
        drawingPanel.repaint();
    }

    private int VerifierEtParse(String nb) {
        if (nb == null || nb.isEmpty()) {
            return 0;
        }
        int valeur;
        try {
            valeur = Integer.parseInt(nb);
            if (valeur <= 0) {
                afficherErreur("Erreur : Dimensions doivent être > 0.");
                return -1;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur : Dimensions invalides.");
            return -1;
        }
        return valeur;
    }

    // FIX division réelle
    private double TransformerVersPouce(int pieds, int pouces, int num, int den) {
        double total = pieds * FACTEUR_CONVERSION_FEET_INCHES + pouces;
        if (num != 0 && den != 0) {
            total += ((double) num / (double) den);
        }
        return total;
    }

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (controller.undo()) {
            reinitialiserPanneauEdition();
            rafraichirVue();
        }
    }

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (controller.redo()) {
            reinitialiserPanneauEdition();
            rafraichirVue();
        }
    }

    // (2) Hexagone/polygone régulier + (3) points du polygone
    private void nouvellePieceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String typePiece = (String) typePieceComboBox.getSelectedItem();

        String largeurPiedText = largeurPiecePiedJText.getText().trim();
        String largeurPouceText = largeurPiecePouceJText.getText().trim();
        String largeurNumText = largeurPieceNumJText.getText().trim();
        String largeurDenText = largeurPieceDenJText.getText().trim();

        String longueurPiedText = longueurPiecePiedJText.getText().trim();
        String longueurPouceText = longueurPiecePouceJText.getText().trim();
        String longueurNumText = longueurPieceNumJText.getText().trim();
        String longueurDenText = longueurPieceDenJText.getText().trim();

        int largeurPied = VerifierEtParse(largeurPiedText);
        int largeurPouce = VerifierEtParse(largeurPouceText);
        int largeurNum = VerifierEtParse(largeurNumText);
        int largeurDen = VerifierEtParse(largeurDenText);

        int longueurPied = VerifierEtParse(longueurPiedText);
        int longueurPouce = VerifierEtParse(longueurPouceText);
        int longueurNum = VerifierEtParse(longueurNumText);
        int longueurDen = VerifierEtParse(longueurDenText);

        if (largeurPied == -1 || largeurPouce == -1 || largeurNum == -1 || largeurDen == -1
                || longueurPied == -1 || longueurPouce == -1 || longueurNum == -1 || longueurDen == -1) {
            return;
        }

        java.awt.Polygon nouvelleForme;

        if ("Irrégulière".equalsIgnoreCase(typePiece) || "Irregulière".equalsIgnoreCase(typePiece)) {
            // Largeur (pieds) = nb côtés (défaut 6)
            int nbCotes = (largeurPied > 0) ? largeurPied : 6;
            if (nbCotes < 3) {
                nbCotes = 3;
            }
            if (nbCotes > 12) {
                nbCotes = 12;
            }

            // Longueur (pieds) = longueur côté en pieds (défaut 3)
            double coteFeet = (longueurPied > 0) ? longueurPied : 3;
            double coteInches = coteFeet * FACTEUR_CONVERSION_FEET_INCHES;

            nouvelleForme = creerPolygoneRegulier(nbCotes, coteInches);
        } else {
            double largeur = TransformerVersPouce(largeurPied, largeurPouce, largeurNum, largeurDen);
            double longueur = TransformerVersPouce(longueurPied, longueurPouce, longueurNum, longueurDen);

            if (largeur == 0 || longueur == 0) {
                largeur = DIMENSION_DEFAUT_PIECE_FEET * FACTEUR_CONVERSION_FEET_INCHES;
                longueur = DIMENSION_DEFAUT_PIECE_FEET * FACTEUR_CONVERSION_FEET_INCHES;
            }

            int largeurPixels = (int) Math.round(largeur);
            int longueurPixels = (int) Math.round(longueur);

            int x = (int) (drawingPanel.getWidth() / DPI - largeur) / 2;
            int y = (int) (drawingPanel.getHeight() / DPI - longueur) / 2;

            int[] xPoints = {x, x + largeurPixels, x + largeurPixels, x};
            int[] yPoints = {y, y, y + longueurPixels, y + longueurPixels};
            nouvelleForme = new java.awt.Polygon(xPoints, yPoints, 4);
        }

        controller.InitialiserPiece(nouvelleForme);
        controller.DefinirDimensionsPiece(largeurPouce, longueurPouce);
        drawingPanel.mettreAJourController(controller);

        afficherPointsPolygone(nouvelleForme); //  points affichés

        reinitialiserPanneauEdition();
        rafraichirVue();
    }

    // Polygone régulier (n côtés) avec côté ≈ sideInches
    private java.awt.Polygon creerPolygoneRegulier(int n, double sideInches) {
        double s = Math.max(1.0, sideInches);
        double R = s / (2.0 * Math.sin(Math.PI / n)); // rayon

        double centerX = (drawingPanel.getWidth() / (double) DPI) / 2.0;
        double centerY = (drawingPanel.getHeight() / (double) DPI) / 2.0;

        int[] x = new int[n];
        int[] y = new int[n];

        // style hexagone “plat en haut” si n pair
        double startAngle = (n % 2 == 0) ? Math.toRadians(-30) : Math.toRadians(-90);

        for (int i = 0; i < n; i++) {
            double a = startAngle + i * (2.0 * Math.PI / n);
            x[i] = (int) Math.round(centerX + R * Math.cos(a));
            y[i] = (int) Math.round(centerY + R * Math.sin(a));
        }
        return new java.awt.Polygon(x, y, n);
    }

    // Affichage des points du polygone
    private void afficherPointsPolygone(java.awt.Polygon poly) {
        if (poly == null || poly.npoints <= 0) {
            return;
        }

        StringBuilder html = new StringBuilder("<html><b>Points du polygone :</b><br/>");
        StringBuilder console = new StringBuilder("Points du polygone:\n");

        for (int i = 0; i < poly.npoints; i++) {
            String p = "(" + poly.xpoints[i] + ", " + poly.ypoints[i] + ")";
            html.append(i).append(": ").append(p).append("<br/>");
            console.append(i).append(": ").append(p).append("\n");
        }
        html.append("</html>");

        ErreurJLabel.setText(html.toString());
        System.out.println(console.toString());
    }

    // --- Thermostat / Membrane / Fil (tes méthodes existantes) ---
    private void ajouterThermostat() {
        if (controller.ObtenirPiece() == null) {
            afficherErreur("Veuillez d'abord créer une pièce avant d'ajouter un thermostat.");
            return;
        }

        int longueurThermostat = 2;
        int largeurThermostat = 2;

        Point positionThermostat = new Point(0, longueurThermostat);

        ThermostatDTO dto = new ThermostatDTO(
                positionThermostat,
                longueurThermostat,
                largeurThermostat
        );

        controller.AjouterThermostat(dto);

        Object selection = controller.SelectionnerElementAvecType(positionThermostat).getElement();
        if (selection instanceof ElementSelectionnableDTO elementDTO) {
            mettreAJourPanneauSelection(elementDTO);
        }
        rafraichirVue();
    }

    private void activerMembrane() {
        if (controller.ObtenirPiece() == null) {
            afficherErreur("Veuillez d'abord créer une pièce avant d'activer la membrane.");
            return;
        }

        controller.InitialiserMembrane(6, 3);
        rafraichirVue();
    }

    private void tracerFilChauffant() {
        if (controller.ObtenirPiece() == null) {
            afficherErreur("Veuillez d'abord créer une pièce.");
            return;
        }

        if (controller.ObtenirThermostat() == null) {
            afficherErreur("Veuillez d'abord créer une pièce avant d'ajouter un thermostat.");
            return;
        }

        if (controller.ObtenirMembrane() == null) {
            afficherErreur("Veuillez d'abord activer la membrane.");
            return;
        }

        int longueurMaxPouces = 0;
        int distanceMaxPouces = 0;

        if (filLongueurMaxJText != null && !filLongueurMaxJText.getText().trim().isEmpty()) {
            try {
                longueurMaxPouces += (int) Math.round(Double.parseDouble(filLongueurMaxJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES);
            } catch (NumberFormatException ex) {
                afficherErreur("Valeur invalide pour la longueur maximale du fil.");
                return;
            }
        }

        if (filLongueurSegmentMaxJText != null && !filLongueurSegmentMaxJText.getText().trim().isEmpty()) {
            try {
                distanceMaxPouces += (int) Math.round(Double.parseDouble(filLongueurSegmentMaxJText.getText().trim()) * FACTEUR_CONVERSION_FEET_INCHES);
            } catch (NumberFormatException ex) {
                afficherErreur("Valeur invalide pour la longueur maximale des segments droits du fil.");
                return;
            }
        }

        controller.TracerFilChauffant(longueurMaxPouces, distanceMaxPouces);
        rafraichirVue();
    }

    private void SauvegarderFichier() {
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnvalue = chooser.showOpenDialog(null);
        if (returnvalue == JFileChooser.APPROVE_OPTION) {

            File directory = chooser.getSelectedFile();
            String path = directory.getAbsolutePath();

            try {
                controller.SauvegarderPiece(path);
            } catch (IOException ex) {
                System.getLogger(MainWindow.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    private void ImporterFichier() {
        JFileChooser chooser;
        chooser = new JFileChooser();
        int returnvalue = chooser.showOpenDialog(null);
        if (returnvalue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String path = file.getAbsolutePath();
            try {
                controller.ImporterPiece(path);
            } catch (IOException ex) {
                System.getLogger(MainWindow.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (ClassNotFoundException ex) {
                System.getLogger(MainWindow.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            rafraichirVue();
        }
    }

    /**
     * Converts a coordinate in inches to feet, inches, numerator, and denominator
     * and updates the provided text fields
     */
    private void afficherCoordonnee(double inches,
                                    JTextField piedsField,
                                    JTextField poucesField,
                                    JTextField numField,
                                    JTextField denField) {
        int pieds = (int) (inches / FACTEUR_CONVERSION_FEET_INCHES);
        double reste = inches - (pieds * FACTEUR_CONVERSION_FEET_INCHES);
        int pouces = (int) reste;
        double fraction = reste - pouces;

        // Convert decimal to fraction (16ths for precision)
        int denominateur = 16;
        int numerateur = (int) Math.round(fraction * denominateur);

        // Simplify fraction
        if (numerateur != 0) {
            int gcd = pgcd(numerateur, denominateur);
            numerateur /= gcd;
            denominateur /= gcd;
        }

        piedsField.setText(String.valueOf(pieds));
        poucesField.setText(String.valueOf(pouces));
        numField.setText(numerateur == 0 ? "0" : String.valueOf(numerateur));
        denField.setText(numerateur == 0 ? "1" : String.valueOf(denominateur));
    }

    /**
     * Calculate greatest common divisor for fraction simplification
     */
    private int pgcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
