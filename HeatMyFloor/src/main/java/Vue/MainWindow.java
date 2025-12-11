package Vue;

import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.ElementSelectionnableDTO;
import Domaine.DTO.MeubleDTO;
import Domaine.DTO.ThermostatDTO;
import Domaine.Entite.ElementSelectionnable;
import Domaine.HeatMyFloorController;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    
// LARGEUR ELEMENT SELECTIONNE
    private javax.swing.JLabel largeurJLabel;
    private javax.swing.JTextField largeurPiedJText, largeurPouceJText, largeurFractionJText, largeurElementNumJText, largeurElementDenJText;
    private javax.swing.JLabel LargeurPouceJLabel, LargeurPiedsJLabel;
    
    // LONGUEUR ELEMENT SELECTIONNE
    private javax.swing.JTextField longueurPiedJText, longueurPouceJText, longueurFractionJText,longueurElementNumJText, longueurElementDenJText;
    private javax.swing.JLabel longueurJLabel;
    private javax.swing.JLabel LongueurPouceJLabel,LongueurPiedsJLabel;
    
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
    private javax.swing.JButton ajoutThermostatButton ;
    private javax.swing.JButton activerMembraneButton ;
    private javax.swing.JButton tracerFilButton ;
    private javax.swing.JButton supprimerMeubleButton;
    private javax.swing.JButton modifierMeubleButton;
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
    private static final int MARGE_MENBRANE_POUCES = (int)Math.round(DISTANCE_SECURITE_METRES * POUCES_PAR_METRE);
    
    private double zoomFactor = 1.0;
    private final double ZOOM_INCREMENT = 0.1;
    private double panOffsetX = 0;
    private double panOffsetY = 0;
    private Point lastMousePosition = null;
    
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
        largeurPiecePiedJText = new javax.swing.JTextField(3);
        largeurPiecePiedJText = new javax.swing.JTextField(3);
        largeurPiecePouceJText = new javax.swing.JTextField(3);
        largeurPieceNumJText = new javax.swing.JTextField(3);
        largeurPieceDenJText = new javax.swing.JTextField(3);
        longueurPieceJLabel = new javax.swing.JLabel();
        largeurPieceJLabel = new javax.swing.JLabel();
        titrePieceJLabel = new javax.swing.JLabel();
        nouvellePieceJButton = new javax.swing.JButton();
        typePieceJLabel = new javax.swing.JLabel();
        typePieceComboBox = new javax.swing.JComboBox<>();
        
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
        buttonTopPanel.add(activerMembraneButton);
        
        tracerFilButton.setText("Tracer Fil Chauffant");
        tracerFilButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            tracerFilChauffant();
        });
        buttonTopPanel.add(tracerFilButton);

        
        supprimerMeubleButton.setText("Supprimer élément sélectionné");
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
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        InformationZoneBottomPanelLayout.setVerticalGroup(
            InformationZoneBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
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
            }
            
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                drawingPanelMouseDragged(e);
            }
        });
        
        drawingPanel.addMouseWheelListener((java.awt.event.MouseWheelEvent evt) -> {
            drawingPanelMouseWheelMoved(evt);
        });
        
//        longueurJLabel.setText("Longueur");
//        LongueurPiedsJLabel.setText("ft");
//        LongueurPouceJLabel.setText("in");
//        LargeurPiedsJLabel.setText("ft");
//        LargeurPouceJLabel.setText("in");
//        largeurJLabel.setText("Largeur");
//        positionXJLabel.setText("Position X");
//        positionYJLabel.setText("Position Y");
//        
//        positionPiedXJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        positionPiedYJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        longueurPiedJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        longueurPouceJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        largeurPiedJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        largeurPouceJText.setPreferredSize(new java.awt.Dimension(25, 22));
//        
//        // CONFIGURATION DES NOUVEAUX CHAMPS POUR LE DRAIN
//        positionDrainXJLabel.setText("Position X");
//        positionDrainYJLabel.setText("Position Y");
//        positionDrainPiedXJText.setPreferredSize(new java.awt.Dimension(100, 22));
//        positionDrainPiedYJText.setPreferredSize(new java.awt.Dimension(100, 22));
        
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

// Create a simple layout WITHOUT inline JLabels
javax.swing.GroupLayout choixLeftPanelLayout = new javax.swing.GroupLayout(choixLeftPanel);
choixLeftPanel.setLayout(choixLeftPanelLayout);

// Labels as separate components (optional, but safe)
javax.swing.JLabel longueurLabel = new javax.swing.JLabel("Longueur (X' Y'' Z/W)");
javax.swing.JLabel largeurLabel = new javax.swing.JLabel("Largeur (X' Y'' Z/W)");
javax.swing.JLabel posXLabel = new javax.swing.JLabel("Position X (X' Y'' Z/W)");
javax.swing.JLabel posYLabel = new javax.swing.JLabel("Position Y (X' Y'' Z/W)");
javax.swing.JLabel diametreLabel = new javax.swing.JLabel("Diamètre Drain (X' Y'' Z/W)");
javax.swing.JLabel positionDrainXLabel = new javax.swing.JLabel("Position X Drain (X' Y'' Z/W)");
javax.swing.JLabel positionDrainYLabel = new javax.swing.JLabel("Position Y Drain (X' Y'' Z/W)");

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
                    .addComponent(positionXElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
             // Position X Drain
                .addComponent(positionDrainXLabel)
                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                    .addComponent(positionDrainPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                // Position Y Drain
                .addComponent(positionDrainYLabel)
                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                    .addComponent(positionDrainPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(positionDrainYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGap(12, 12, 12)
            .addComponent(largeurLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(largeurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(largeurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(largeurElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(largeurElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(posXLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(positionPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionXElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionXElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(posYLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(positionPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionYElementNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionYElementDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(diametreLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(diametrePiedJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(diametrePouceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(diametreNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(diametreDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            // Position X Drain
            .addComponent(positionDrainXLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(positionDrainPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainPouceXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainXNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainXDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            // Position Y Drain
            .addComponent(positionDrainYLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(positionDrainPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainPouceYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainYNumJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(positionDrainYDenJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))    
            .addContainerGap(20, Short.MAX_VALUE))
);
        mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST);
        
        drawingCenterPanel = new javax.swing.JPanel();
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(220, 350));
        java.awt.BorderLayout drawingCenterPanelLayout = new java.awt.BorderLayout();
        drawingCenterPanel.setLayout(drawingCenterPanelLayout);
        
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);
        
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
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
        typePieceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regulière", "Irrégulière" }));
        
       javax.swing.GroupLayout choixRightPanelLayout = new javax.swing.GroupLayout(choixRightPanel);
choixRightPanel.setLayout(choixRightPanelLayout);

// Labels
javax.swing.JLabel pieceTitleLabel = new javax.swing.JLabel("Pièce");
javax.swing.JLabel typeLabel = new javax.swing.JLabel("Type");
javax.swing.JLabel longueurPieceLabel = new javax.swing.JLabel("Longueur (X' Y'' Z/W)");
javax.swing.JLabel largeurPieceLabel = new javax.swing.JLabel("Largeur (X' Y'' Z/W)");

// Champs (déjà existants)
//longueurPieceJText.setPreferredSize(new java.awt.Dimension(40, 22));
//largeurPieceJText.setPreferredSize(new java.awt.Dimension(40, 22));

choixRightPanelLayout.setHorizontalGroup(
    choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(choixRightPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                // Titre
                .addComponent(pieceTitleLabel)

                // Type
                .addComponent(typeLabel)
                .addComponent(typePieceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)

                .addGap(12)

                // Longueur pièce
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

                .addGap(12)

                // Largeur pièce
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

                .addGap(18)

                // Bouton
                .addComponent(nouvellePieceJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)

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

            // Longueur pièce
            .addComponent(longueurPieceLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(longueurPiecePiedJText)
                .addComponent(longueurPiecePouceJText)
                .addComponent(longueurPieceNumJText)
                .addComponent(longueurPieceDenJText)
            )

            .addGap(20)

            // Largeur pièce
            .addComponent(largeurPieceLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(largeurPiecePiedJText)
                .addComponent(largeurPiecePouceJText)
                .addComponent(largeurPieceNumJText)
                .addComponent(largeurPieceDenJText)
            )

            .addGap(20)

            .addComponent(nouvellePieceJButton)

            .addContainerGap(80, Short.MAX_VALUE)
        )
);
        
        mainPanel.add(choixRightPanel, java.awt.BorderLayout.EAST);
        
        fichierMenu.setText("Fichier");
        
        nouvelleModelisationMenu.setText("Nouvelle modélisation");
        
        pieceReguliereItem.setText("Pièce regulière");
        nouvelleModelisationMenu.add(pieceReguliereItem);
        
        pieceIrreguliereItem.setText("Pièce irrégulière");
        nouvelleModelisationMenu.add(pieceIrreguliereItem);
        
        fichierMenu.add(nouvelleModelisationMenu);
        
        sauvegarderItem.setText("Sauvegarder");
        fichierMenu.add(sauvegarderItem);
        
        exporterItem.setText("Exporter");
        fichierMenu.add(exporterItem);
        
        importerItem.setText("Importer");
        fichierMenu.add(importerItem);
        
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
    
            // Helper to build a row: label + 4 fields
JPanel row(String label, JTextField ft, JTextField in, JTextField num, JTextField den) {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 2));
    p.add(new JLabel(label));
    p.add(new JLabel("ft")); p.add(ft);
    p.add(new JLabel("in")); p.add(in);
    p.add(new JLabel("  "));
    p.add(num); p.add(new JLabel("/")); p.add(den);
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
        
//        largeurPieceJText.setText(Integer.toString(positionSouris.x) + ", " + Integer.toString(positionMonde.x));
//        longueurPieceJText.setText(Integer.toString(positionSouris.y) + ", " + Integer.toString(positionMonde.y));
        
        Object selection = controller.SelectionnerElement(positionMonde);
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
        
        Object selection = controller.SelectionnerElement(positionMonde);
        
        if (selection instanceof ElementSelectionnableDTO) {
            controller.saveStateBeforeDrag();
            
            isDragging = true;
            dragStartPoint = positionSouris;
            elementStartPosition = ((ElementSelectionnableDTO) selection).getPosition();
            
            drawingPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.MOVE_CURSOR));
        }
        
        rafraichirVue();
    }
    
    private void drawingPanelMouseDragged(java.awt.event.MouseEvent e){ 
        if (!isDragging || dragStartPoint == null || elementStartPosition == null) {
            return;
        }
        
        Point positionActuelle = e.getPoint();
        
        int deltaX = positionActuelle.x - dragStartPoint.x;
        int deltaY = positionActuelle.y - dragStartPoint.y;
        
        int deltaXMonde = (int) (deltaX / (zoomFactor * DPI));
        int deltaYMonde = -(int) (deltaY / (zoomFactor * DPI));
        
        Point nouvellePosition = new Point(
            elementStartPosition.x + deltaXMonde,
            elementStartPosition.y + deltaYMonde
        );
        
        Object elementSelectionne = controller.ObtenirElementSelectionne();
        
        if (elementSelectionne instanceof ElementSelectionnableDTO) {
            ElementSelectionnableDTO element = (ElementSelectionnableDTO) elementSelectionne;
            
            controller.ModifierElementSelectionne(
                    nouvellePosition,
                    element.getLargeur(),
                    element.getLongueur()
            );
            rafraichirVue();
        }
    }
    
    private void drawingPanelMouseReleased(java.awt.event.MouseEvent e) { 
        if (isDragging) {
            isDragging = false;
            dragStartPoint = null;
            elementStartPosition = null;
            
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
        Object selection = controller.SelectionnerElement(positionMeuble);
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
        //Point positionInitiale = convertirPosition(0, 0);
        
        Point positionElementChauffant = new Point(0, 0);
        ElementChauffantDTO dto = new ElementChauffantDTO(positionElementChauffant, LONGUEUR_INITIALE_ELTCHAUFFANT_POUCES, LARGEUR_INITIALE_ELTCHAUFFANT_POUCES);
        controller.AjouterElementChauffant(dto);
        rafraichirVue();
        
        Object elementAjoute = controller.ObtenirElementSelectionne();
        if (elementAjoute == null) {
            Object selection = controller.SelectionnerElement(positionElementChauffant);
            if (selection instanceof ElementSelectionnableDTO elementDTO) {
                mettreAJourPanneauSelection(elementDTO);
            }
        }
        rafraichirVue();
    }
    
    private int convertInchesToPixels(int valeurEnPouces) {
        int valeurEnPixels = (int) Math.round(valeurEnPouces * DPI);
        return valeurEnPixels;
    }
    
    private int convertPixelsToInches(int valeurEnPixels) {
        int valeurEnPouces = (int) Math.round(valeurEnPixels / DPI);
        return valeurEnPouces;
    }
    
    private void supprimerMeubleButtonActionPerformed(java.awt.event.ActionEvent e) {
        Object elementSelectionne = controller.ObtenirElementSelectionne(); 
        
        if (elementSelectionne == null) {
            javax.swing.JOptionPane.showMessageDialog(
            this,
                "Aucun élément sélectionné.",
                "Erreur",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String nomElement = "";
        if (elementSelectionne instanceof MeubleDTO) {
            MeubleDTO meuble = (MeubleDTO) elementSelectionne;
            nomElement = meuble.getNom();
        } else if (elementSelectionne instanceof ElementChauffantDTO)  {
            nomElement = "Élément Chauffant";
        }
        
        int reponse = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Êtes-vous sûr de vouloir supprimer cet élément ?\n\nType : " + nomElement,
            "Confirmation de suppression",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        if (reponse == javax.swing.JOptionPane.YES_OPTION) {
        boolean supprime = controller.SupprimerElementSelectionne();
        
        if (supprime) {
           reinitialiserPanneauEdition();
           rafraichirVue();
        } else {
            afficherErreur("La suppression a échoué.");
        }
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
    
    if (!positionPiedXJText.getText().trim().isEmpty())  {
        try {
            positionX += Integer.parseInt(positionPiedXJText.getText().trim())* FACTEUR_CONVERSION_FEET_INCHES;
        } catch (NumberFormatException ex) {
            return;
        }
    }
    if (!positionPouceXJText.getText().trim().isEmpty())  {
        try {
            positionX += Integer.parseInt(positionPouceXJText.getText().trim());
        } catch (NumberFormatException ex) {
            return;
        }
    }
     
    if (!positionPiedYJText.getText().trim().isEmpty()) {
        try {
            positionY += Integer.parseInt(positionPiedYJText.getText().trim())* FACTEUR_CONVERSION_FEET_INCHES;
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
     
    // TRAITER LE DRAIN AVANT DE MODIFIER L'ÉLÉMENT
    if (meubleDto != null && MEUBLES_AVEC_DRAIN.contains(meubleDto.getNom())) {
        ElementSelectionnable element = controller.ObtenirElementSelectionneDirect();
        
        if (element instanceof Domaine.Entite.MeubleAvecDrain meubleAvecDrain) {
            // Modifier le diamètre du drain
            String diamText = diametrePiedJText.getText().trim();
            String diamTextPouce = diametrePouceJText.getText().trim();
            if (!diamText.isEmpty()||!diamTextPouce.isEmpty()) {
                try {
                    int diamInches = Integer.parseInt(diamText)*FACTEUR_CONVERSION_FEET_INCHES;
                     diamInches += Integer.parseInt(diamTextPouce);
                    if (diamInches > 0) {
                        meubleAvecDrain.setDiametreDrain(diamInches);
                        System.out.println("Diamètre drain modifié: " + diamInches);
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Diamètre invalide");
                }
            }
            
            // MODIFIER LA POSITION DU DRAIN
            int drainX = -1;
            int drainY = -1;
            boolean drainPosModifiee = false;
            
            // Parse drain X (pieds + pouces)
            String drainXPiedText = positionDrainPiedXJText.getText().trim();
            String drainXPouceText = positionDrainPouceXJText.getText().trim();
            
            if (!drainXPiedText.isEmpty() || !drainXPouceText.isEmpty()) {
                drainX = 0;
                if (!drainXPiedText.isEmpty()) {
                    try {
                        drainX += Integer.parseInt(drainXPiedText) * FACTEUR_CONVERSION_FEET_INCHES;
                    } catch (NumberFormatException ex) {
                        System.err.println("Erreur parse drain X pieds");
                    }
                }
                if (!drainXPouceText.isEmpty()) {
                    try {
                        drainX += Integer.parseInt(drainXPouceText);
                    } catch (NumberFormatException ex) {
                        System.err.println("Erreur parse drain X pouces");
                    }
                }
                drainPosModifiee = true;
            }
            
            // Parse drain Y (pieds + pouces)
            String drainYPiedText = positionDrainPiedYJText.getText().trim();
            String drainYPouceText = positionDrainPouceYJText.getText().trim();
            
            if (!drainYPiedText.isEmpty() || !drainYPouceText.isEmpty()) {
                drainY = 0;
                if (!drainYPiedText.isEmpty()) {
                    try {
                        drainY += Integer.parseInt(drainYPiedText) * FACTEUR_CONVERSION_FEET_INCHES;
                    } catch (NumberFormatException ex) {
                        System.err.println("Erreur parse drain Y pieds");
                    }
                }
                if (!drainYPouceText.isEmpty()) {
                    try {
                        drainY += Integer.parseInt(drainYPouceText);
                    } catch (NumberFormatException ex) {
                        System.err.println("Erreur parse drain Y pouces");
                    }
                }
            }
            
            // Appliquer la nouvelle position du drain
            if (drainPosModifiee && drainX >= 0 && drainY >= 0) {
                System.out.println("Tentative de déplacement drain vers: (" + drainX + ", " + drainY + ")");
                System.out.println("Limites meuble: largeur=" + largeurConvertie + ", longueur=" + longueurConvertie);
                
                if (drainX <= largeurConvertie && drainY <= longueurConvertie) {
                    Point nouveauCentre = new Point(drainX, drainY);
                    meubleAvecDrain.setCentreDrain(nouveauCentre);
                    System.out.println("Position drain mise à jour: " + nouveauCentre);
                    System.out.println("Vérification après set: " + meubleAvecDrain.getCentreDrain());
                } else {
                    System.out.println("Position drain hors limites du meuble");
                }
            }
        }
    }
    
    // MODIFIER L'ÉLÉMENT (position, largeur, longueur)
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
    
    private Point convertirPosition(int x, int y) {
        Point origine = controller.ObtenirOrigine();
        return new Point(origine.x + x, origine.y - y);
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
    positionPiedXJText.setText(Integer.toString(positionBase.x/FACTEUR_CONVERSION_FEET_INCHES));
    positionPiedYJText.setText(Integer.toString((positionBase.y - longueur)/ FACTEUR_CONVERSION_FEET_INCHES));
    positionPouceXJText.setText(Integer.toString(positionBase.x%FACTEUR_CONVERSION_FEET_INCHES));
    positionPouceYJText.setText(Integer.toString((positionBase.y - longueur)% FACTEUR_CONVERSION_FEET_INCHES));
    
    if (element instanceof MeubleDTO meuble && meuble.estAvecDrain()) {
        diametrePiedJText.setText(Integer.toString(meuble.getDiametreDrain()/FACTEUR_CONVERSION_FEET_INCHES));
        diametrePouceJText.setText(Integer.toString(meuble.getDiametreDrain()%FACTEUR_CONVERSION_FEET_INCHES));
        diametrePiedJText.setEnabled(true);
        
        // AFFICHER LA POSITION DU DRAIN
        Point centreDrain = meuble.getCentreDrain();
        if (centreDrain != null) {
            // AJOUT DE DEBUG
            // System.out.println("=== MISE À JOUR PANNEAU ===");
            // System.out.println("Centre drain du DTO: " + centreDrain);
            positionDrainPiedXJText.setText(Integer.toString((int)(centreDrain.x / FACTEUR_CONVERSION_FEET_INCHES)));
            positionDrainPiedYJText.setText(Integer.toString((int)(centreDrain.y / FACTEUR_CONVERSION_FEET_INCHES)));
            positionDrainPiedXJText.setEnabled(true);
            positionDrainPiedYJText.setEnabled(true);
            positionDrainPouceXJText.setText(Integer.toString((int)(centreDrain.x % FACTEUR_CONVERSION_FEET_INCHES)));
            positionDrainPouceYJText.setText(Integer.toString((int)(centreDrain.y % FACTEUR_CONVERSION_FEET_INCHES)));
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
        // TODO : A completer 
        javax.swing.JOptionPane.showMessageDialog(
            this,
            message,
            "Erreur",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void rafraichirVue() {
        drawingPanel.repaint();
    }
    
    
    private int VerifierEtParse(String nb)
    {
        int valeur;
     if(nb.isEmpty())
     {
         return 0;
     }
        try {
                valeur = Integer.parseInt(nb);
                if (valeur <= 0 ) {
                    System.err.println("Erreur : Dimensions doivent être > 0.");
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.err.println("Erreur : Dimensions invalides.");
                return -1;
            }
        return valeur;
    }
    
    
    private double TransformerVersPouce(int pieds,int pouces, int num,int den)
    {
    double total;
    
    total = pieds*FACTEUR_CONVERSION_FEET_INCHES + pouces;
    if(num !=0 && den !=0)
    {
        total += (num/den);
    }
    return total;
    }
    
    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("BUTTON UNDO CLIQUE !!!");
        javax.swing.JOptionPane.showMessageDialog(this, "Undo clique!");
        
        if (controller.undo()){
            reinitialiserPanneauEdition();
            rafraichirVue();
        }            
    }
    
    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("BUTTON REDO CLIQUE !!!");
        javax.swing.JOptionPane.showMessageDialog(this, "Redo clique!");
        
        if(controller.redo()) {
            reinitialiserPanneauEdition();
            rafraichirVue();
        }
    }
    
    private void nouvellePieceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String largeurPiedText = largeurPiecePiedJText.getText().trim();
        String largeurPouceText = largeurPiecePouceJText.getText().trim();
        String largeurNumText = largeurPieceNumJText.getText().trim();
        String largeurDenText = largeurPieceDenJText.getText().trim();
        String longueurPiedText = longueurPiecePiedJText.getText().trim();
        String longueurPouceText = longueurPiecePouceJText.getText().trim();
        String longueurNumText = longueurPieceNumJText.getText().trim();
        String longueurDenText = longueurPieceDenJText.getText().trim();
        int largeurPied,largeurPouce,largeurNum,largeurDen,longueurPied,longueurPouce,longueurNum,longueurDen;
        double largeur,longueur;
        
  
        largeurPied = VerifierEtParse(largeurPiedText);
        largeurPouce = VerifierEtParse(largeurPouceText);
        largeurNum = VerifierEtParse(largeurNumText);
        largeurDen = VerifierEtParse(largeurDenText);
        longueurPied = VerifierEtParse(longueurPiedText);
        longueurPouce = VerifierEtParse(longueurPouceText);
        longueurNum = VerifierEtParse(longueurNumText);
        longueurDen = VerifierEtParse(longueurDenText);
        
        if(largeurPied ==-1 || largeurPouce ==-1 || largeurNum==-1 ||largeurDen ==-1 || longueurPied == -1 || longueurPouce == -1 || longueurNum == -1 || longueurDen ==-1)
        {
            return;
        }
        largeur = TransformerVersPouce(largeurPied,largeurPouce,largeurNum,largeurDen);
        longueur = TransformerVersPouce(longueurPied,longueurPouce,longueurNum,longueurDen);
        
        if (largeur == 0 || longueur == 0)
        {
            largeur = DIMENSION_DEFAUT_PIECE_FEET * FACTEUR_CONVERSION_FEET_INCHES;
            longueur = DIMENSION_DEFAUT_PIECE_FEET * FACTEUR_CONVERSION_FEET_INCHES;
        }
        
        //todo changer pour pouvoir avoir double
        
        int largeurPixels = (int)largeur; //convertInchesToPixels((int)largeur);
        int longueurPixels = (int)longueur; //convertInchesToPixels((int)longueur);
        
        int x = (int)(drawingPanel.getWidth() / DPI - largeur) / 2;
        int y = (int)(drawingPanel.getHeight() / DPI - longueur) / 2;
        
        //String typePiece = (String) typePieceComboBox.getSelectedItem();
        java.awt.Polygon nouvelleForme;
        
//        if ("Irregulière".equals(typePiece)) {
//            int[] xPoints = {x, x + largeurPixels, x + largeurPixels, x + largeurPixels / 2, x};
//            int[] yPoints = {y + longueurPixels / 3, y + longueurPixels / 3, y + longueurPixels, y + longueurPixels, y + longueurPixels / 3};
//            nouvelleForme = new java.awt.Polygon(xPoints, yPoints, 5);
//        } else {
            int[] xPoints = {x, x + largeurPixels, x + largeurPixels, x};
           int[] yPoints = {y, y, y + longueurPixels, y + longueurPixels};
            nouvelleForme = new java.awt.Polygon(xPoints, yPoints, 4);
//        }
        
        //controller = new HeatMyFloorController();
        controller.InitialiserPiece(nouvelleForme);
        controller.DefinirDimensionsPiece(largeurPouce, longueurPouce);
        drawingPanel.mettreAJourController(controller);
        
        reinitialiserPanneauEdition();
//        largeurPieceJText.setText("");
//        longueurPieceJText.setText("");
        rafraichirVue();
        
//        System.out.println("Pièce '" + typePiece + "' (" + largeur + "x" + longueur + ") créée.");
    }
    
    private java.awt.Polygon creerFormeIrreguliere(int largeur, int longueur) {
        int x = (drawingPanel.getWidth() - largeur) / 2;
        int y = (drawingPanel.getHeight() - longueur) / 2;
        
        int[] xPoints = {x, x + largeur, x + largeur, x + largeur / 2, x};
        int[] yPoints = {y, y, y + (longueur * 2 / 3), y + longueur, y + (longueur * 2 / 3)};
        
        return new java.awt.Polygon(xPoints, yPoints, 5);
    }
    
    // TODO  ADJUST
    private double parseMeasurement(JTextField feet, JTextField inches, JTextField num, JTextField den) {
        double total = 0.0;
        try {
            if (!feet.getText().trim().isEmpty())
                total += Integer.parseInt(feet.getText().trim()) * 12;
            if (!inches.getText().trim().isEmpty())
                total += Integer.parseInt(inches.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // invalid
        }

        // Parse fraction
        if (!num.getText().trim().isEmpty() && !den.getText().trim().isEmpty()) {
            try {
                int n = Integer.parseInt(num.getText().trim());
                int d = Integer.parseInt(den.getText().trim());
                if (d > 0) total += (double) n / d;
            } catch (NumberFormatException ignored) {}
        }
        return total; // in inches
    }
    
    // Set 4 fields from total inches
    private void setMeasurement(double totalInches, JTextField feet, JTextField inches, JTextField num, JTextField den) {
        if (totalInches < 0) {
            feet.setText(""); inches.setText(""); num.setText(""); den.setText("");
            return;
        }

        int wholeInches = (int) Math.floor(totalInches);
        int ft = wholeInches / 12;
        int in = wholeInches % 12;
        double remainder = totalInches - wholeInches;

        feet.setText(String.valueOf(ft));
        inches.setText(String.valueOf(in));

        // Simple fraction approximation: check common denominators up to 64
        if (remainder == 0) {
            num.setText("");
            den.setText("");
        } else {
            int bestN = 1, bestD = 1;
            double bestError = Math.abs(remainder - 1.0);
            for (int d = 2; d <= 64; d++) {
                int n = (int) Math.round(remainder * d);
                if (n == 0) continue;
                double error = Math.abs(remainder - (double) n / d);
                if (error < bestError) {
                    bestError = error;
                    bestN = n;
                    bestD = d;
                }
            }
            num.setText(String.valueOf(bestN));
            den.setText(String.valueOf(bestD));
        }
    }

    private void ajouterThermostat() {
        if (controller.ObtenirPiece() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez d'abord créer une pièce avant d'ajouter un thermostat.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Dimensions du thermostat (2x2 pouces)
        int longueurThermostat = 2;
        int largeurThermostat = 2;
        
        // Position initiale sur le mur du bas (y=0) au centre de la largeur    
        Point positionThermostat = new Point(0, longueurThermostat);
        
        ThermostatDTO dto = new ThermostatDTO(
            positionThermostat, 
            longueurThermostat, 
            largeurThermostat
        );
        
        controller.AjouterThermostat(dto );
        
        Object selection = controller.SelectionnerElement(positionThermostat);
        if (selection instanceof ElementSelectionnableDTO elementDTO) {
            mettreAJourPanneauSelection(elementDTO);
        }
        rafraichirVue();
    }
    
    private void activerMembrane() {
        if (controller.ObtenirPiece() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez d'abord créer une pièce avant d'activer la membrane.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Initialiser la membrane avec espacement de 6 pouces et marge de 3 pouces
        controller.InitialiserMenbrane(6, 3);
        rafraichirVue();
    }
    
    private void tracerFilChauffant() {
        if (controller.ObtenirPiece() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez d'abord créer une pièce.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (controller.ObtenirThermostat() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez d'abord ajouter un thermostat.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (controller.ObtenirMenbrane() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez d'abord activer la membrane.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Demander la longueur maximale du fil
        String longueurInput = javax.swing.JOptionPane.showInputDialog(
            this,
            "Longueur maximale du fil (en pieds) :",
            "25"
        );
        if (longueurInput == null || longueurInput.trim().isEmpty()) {
            return;
        }
        
        int longueurMaxPouces;
        try {
            double longueurPieds = Double.parseDouble(longueurInput);
            longueurMaxPouces = (int) Math.round(longueurPieds * FACTEUR_CONVERSION_FEET_INCHES);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Valeur invalide pour la longueur.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Demander la distance maximale par ligne droite
        String distanceInput = javax.swing.JOptionPane.showInputDialog(
            this,
            "Distance maximale par ligne droite (en pieds) :",
            "10"
        );
        if (distanceInput == null || distanceInput.trim().isEmpty()) {
            return;
        }
        
        int distanceMaxPouces;
        try {
            double distancePieds = Double.parseDouble(distanceInput);
            distanceMaxPouces = (int) Math.round(distancePieds * FACTEUR_CONVERSION_FEET_INCHES);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Valeur invalide pour la distance.",
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        controller.TracerFilChauffant(longueurMaxPouces, distanceMaxPouces);
        rafraichirVue();

    }

}