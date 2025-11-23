package Vue;

import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.ElementSelectionnableDTO;
import Domaine.DTO.MeubleDTO;
import Domaine.Entite.ElementSelectionnable;
import Domaine.HeatMyFloorController;
import java.awt.Point;
import java.awt.Toolkit;
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
    private javax.swing.JLabel largeurJLabel;
    private javax.swing.JTextField largeurPiedJText;
    private javax.swing.JTextField largeurPouceJText;
    private javax.swing.JTextField largeurFractionJText;
    private javax.swing.JTextField longueurPiedsJText;
    private javax.swing.JTextField longueurPouceJText;
    private javax.swing.JTextField longueurFractionJText;
    private javax.swing.JLabel longueurJLabel;
    private javax.swing.JLabel LongueurPouceJLabel;
    private javax.swing.JLabel LongueurPiedsJLabel;
    private javax.swing.JLabel LargeurPouceJLabel;
    private javax.swing.JLabel LargeurPiedsJLabel;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> modelisationTypesBox;
    private javax.swing.JMenu nouvelleModelisationMenu;
    private javax.swing.JMenu outilsMenu1;
    private javax.swing.JMenuItem pieceIrreguliereItem;
    private javax.swing.JMenuItem pieceReguliereItem;
    private javax.swing.JLabel positionXJLabel;
    private javax.swing.JLabel positionPiedXJLabel;
    private javax.swing.JLabel positionPouceXJLabel;
    private javax.swing.JTextField positionPiedXJText;
    private javax.swing.JTextField positionPouceXJText;
    private javax.swing.JLabel positionYJLabel;
    private javax.swing.JTextField positionPiedYJText;
    private javax.swing.JTextField positionPouceYJText;
    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem sauvegarderItem;
    private javax.swing.JButton ajoutMeubleSDButton;
    private javax.swing.JButton ajoutElementChauffantButton;
    private javax.swing.JButton supprimerMeubleButton;
    private javax.swing.JButton modifierMeubleButton;
    private javax.swing.JButton undoButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JButton zoomResetButton;
    private javax.swing.JLabel zoomLabel;
    private javax.swing.JComboBox<String> meubleSansDrainBox;
    private javax.swing.JLabel diametreJLabel;
    private javax.swing.JTextField diametreJText;
    private javax.swing.JTextField largeurPieceJText;
    private javax.swing.JTextField longueurPieceJText;
    private javax.swing.JLabel longueurPieceJLabel;
    private javax.swing.JLabel largeurPieceJLabel;
    private javax.swing.JLabel titrePieceJLabel;
    private javax.swing.JButton nouvellePieceJButton;
    private javax.swing.JLabel typePieceJLabel;
    private javax.swing.JComboBox<String> typePieceComboBox;
    
    private final int DIAMETRE_DRAIN_PIXELS = 10;
    private final int LONGUEUR_INITIALE_MEUBLE = 1;
    private final int LARGEUR_INITIALE_MEUBLE = 1;

    // Variables pour le zoom
    private double zoomFactor = 1.0;
    private final double ZOOM_INCREMENT = 0.1;
    

    // Offset pour le zoom centré sur la souris
    private double panOffsetX = 0;
    private double panOffsetY = 0;

    // Position de la dernière souris pour le zoom
    private Point lastMousePosition = null;

    public HeatMyFloorController controller;
    public DrawingPanel drawingPanel;

    public MainWindow() {
        controller = new HeatMyFloorController();
        drawingPanel = new DrawingPanel(this);

        int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
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
        supprimerMeubleButton = new javax.swing.JButton();
        modifierMeubleButton = new javax.swing.JButton();
        longueurPiedsJText = new javax.swing.JTextField();
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
        diametreJText = new javax.swing.JTextField();
        diametreJLabel = new javax.swing.JLabel();
        longueurPieceJText = new javax.swing.JTextField();
        largeurPieceJText = new javax.swing.JTextField();
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
        buttonTopPanel.add(undoButton);

        redoButton.setText("Redo");
        buttonTopPanel.add(redoButton);

        // Boutons de zoom
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

        diametreJText.setPreferredSize(new java.awt.Dimension(100, 22));
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
                drawingCenterPanelMouseClicked(e);
            }
        });

        // Suivre la position de la souris
        drawingPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                lastMousePosition = e.getPoint();
            }
        });

        drawingPanel.addMouseWheelListener((java.awt.event.MouseWheelEvent evt) -> {
            drawingPanelMouseWheelMoved(evt);
        });
        
        // Coté gauche
        longueurJLabel.setText("Longueur");
        LongueurPiedsJLabel.setText("ft");
        LongueurPouceJLabel.setText("in");
         LargeurPiedsJLabel.setText("ft");
        LargeurPouceJLabel.setText("in");
        largeurJLabel.setText("Largeur");
        positionXJLabel.setText("Position X");
        positionYJLabel.setText("Position Y");
        positionPiedXJText.setPreferredSize(new java.awt.Dimension(25, 22));
        positionPiedYJText.setPreferredSize(new java.awt.Dimension(25, 22));
        longueurPiedsJText.setPreferredSize(new java.awt.Dimension(25, 22));
        longueurPouceJText.setPreferredSize(new java.awt.Dimension(25, 22));
        largeurPiedJText.setPreferredSize(new java.awt.Dimension(25, 22));
        largeurPouceJText.setPreferredSize(new java.awt.Dimension(25, 22));
        
        choixLeftPanel = new javax.swing.JPanel();
        choixLeftPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixLeftPanel.setBackground(java.awt.Color.WHITE);
        choixLeftPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));

        javax.swing.GroupLayout choixLeftPanelLayout = new javax.swing.GroupLayout(choixLeftPanel);
       choixLeftPanel.setLayout(choixLeftPanelLayout);

choixLeftPanelLayout.setHorizontalGroup(
    choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(choixLeftPanelLayout.createSequentialGroup()
            .addGap(29, 29, 29)
            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                // ----- LONGUEUR -----
                .addComponent(longueurJLabel)

                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                    .addComponent(longueurPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(LongueurPiedsJLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(longueurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LongueurPouceJLabel)
                )

                // ----- LARGEUR -----
                .addComponent(largeurJLabel)

                .addGroup(choixLeftPanelLayout.createSequentialGroup()
                    .addComponent(largeurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(LargeurPiedsJLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(largeurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LargeurPouceJLabel)
                )

                // ----- POSITION X -----
                .addComponent(positionXJLabel)
                .addComponent(positionPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

                // ----- POSITION Y -----
                .addComponent(positionYJLabel)
                .addComponent(positionPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

                // ----- DIAMETRE -----
                .addComponent(diametreJLabel)
                .addComponent(diametreJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addContainerGap(57, Short.MAX_VALUE))
);


choixLeftPanelLayout.setVerticalGroup(
    choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(choixLeftPanelLayout.createSequentialGroup()
            .addGap(12, 12, 12)

            // ----- LONGUEUR -----
            .addComponent(longueurJLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(longueurPiedsJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(LongueurPiedsJLabel)
                .addComponent(longueurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(LongueurPouceJLabel)
            )

            .addGap(24, 24, 24)

            // ----- LARGEUR -----
            .addComponent(largeurJLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)

            .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(largeurPiedJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(LargeurPiedsJLabel)
                .addComponent(largeurPouceJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(LargeurPouceJLabel)
            )

            .addGap(24, 24, 24)

            // ----- POSITION X -----
            .addComponent(positionXJLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(positionPiedXJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

            .addGap(24, 24, 24)

            // ----- POSITION Y -----
            .addComponent(positionYJLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(positionPiedYJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

            .addGap(24, 24, 24)

            // ----- DIAMETRE -----
            .addComponent(diametreJLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(diametreJText, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

            .addContainerGap(26, Short.MAX_VALUE))
);

        mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST);

        drawingCenterPanel = new javax.swing.JPanel();
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(250, 350));
        java.awt.BorderLayout drawingCenterPanelLayout = new java.awt.BorderLayout();
        drawingCenterPanel.setLayout(drawingCenterPanelLayout);
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);

        
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixRightPanel.setBackground(java.awt.Color.WHITE);
        choixRightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));
        
        // Coté droit
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        longueurPieceJLabel.setText("Longueur");
        largeurPieceJLabel.setText("Largeur");
        titrePieceJLabel.setText("Pièce ");
        nouvellePieceJButton.setText("Nouvelle piece");
        nouvellePieceJButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            nouvellePieceButtonActionPerformed(evt);
        }); 
        typePieceJLabel.setText("Type");
        typePieceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regulière", "Irrégulière" }));
        longueurPieceJText.setPreferredSize(new java.awt.Dimension(100, 22));
        largeurPieceJText.setPreferredSize(new java.awt.Dimension(100, 22));

        javax.swing.GroupLayout choixRightPanelLayout = new javax.swing.GroupLayout(choixRightPanel);

        choixRightPanelLayout.setHorizontalGroup(
            choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixRightPanelLayout.createSequentialGroup()
                .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(choixRightPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titrePieceJLabel)))
                            .addComponent(typePieceJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typePieceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(longueurPieceJLabel)
                            .addComponent(longueurPieceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(largeurPieceJLabel)))
                            .addComponent(largeurPieceJText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nouvellePieceJButton)
                    .addGroup(choixRightPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        choixRightPanelLayout.setVerticalGroup(
            choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixRightPanelLayout.createSequentialGroup()
                .addComponent(titrePieceJLabel)
                .addGap(24, 24, 24)
                .addComponent(typePieceJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typePieceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(longueurPieceJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longueurPieceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(largeurPieceJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(largeurPieceJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(nouvellePieceJButton)
                .addContainerGap(137, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
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

          // Empecher zoom = 0 ou negatif
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

        Object selection = controller.SelectionnerElement(positionMonde);
        if (selection instanceof ElementSelectionnableDTO elementDTO) {
            mettreAJourPanneauSelection(elementDTO);
        }else {
            reinitialiserPanneauEdition();
        }
        rafraichirVue();
    }

    private void drawingPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {

        int rotation = evt.getWheelRotation();
        Point mousePos = evt.getPoint();

        if (rotation < 0) {
                zoomAt(mousePos, ZOOM_INCREMENT);
            }
         else {
            zoomAt(mousePos, -ZOOM_INCREMENT);
            
        }
    }

    private Point screenToWorld(Point screenPos) {
        int worldX = (int) ((screenPos.x - panOffsetX) / zoomFactor);
        int worldY = (int) ((screenPos.y - panOffsetY) / zoomFactor);
        return new Point(worldX, worldY);
    }

    private Point worldToScreen(Point worldPos) {
        int screenX = (int) (worldPos.x * zoomFactor + panOffsetX);
        int screenY = (int) (worldPos.y * zoomFactor + panOffsetY);
        return new Point(screenX, screenY);
    }

    private void ajouterMeuble(String typeMeuble) {
        if (typeMeuble == null || typeMeuble.isBlank()) {
            typeMeuble = "MEUBLE";
        }

        Point positionInitiale = convertirPosition(0, 0);
        int longueur = convertInchesToPixels(LONGUEUR_INITIALE_MEUBLE);
        int largeur = convertInchesToPixels(LARGEUR_INITIALE_MEUBLE);
        Point positionMeuble = new Point(positionInitiale.x, positionInitiale.y - longueur);

        MeubleDTO dto;
        if (MEUBLES_AVEC_DRAIN.contains(typeMeuble)) {

            Point centreDrain = new Point(largeur / 2, longueur / 2);
            dto = new MeubleDTO(positionMeuble, longueur, largeur, typeMeuble, centreDrain);

        } else {

            dto = new MeubleDTO(positionMeuble, longueur, largeur, typeMeuble);

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

        // TODO -  : Considérer le diamètre du drain pour les cas avec drain
        ajouterMeuble(typeMeuble);
    }
    
    private void ajoutElementChauffantButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
        ajouterElementchauffant();
    }
    
    private void ajouterElementchauffant(){
        
        //point de depart
        //Point origine = controller.ObtenirOrigine();
        
        Point positionInitiale = convertirPosition(0, 0);
        int longueur = convertInchesToPixels(1);
        int largeur = convertInchesToPixels(1);
        
        Point positionElementChauffant = new Point(positionInitiale.x , positionInitiale.y - longueur);
        
        ElementChauffantDTO dto = new ElementChauffantDTO(positionElementChauffant, longueur, largeur);
        
        controller.AjouterElementChauffant(dto);
        
        rafraichirVue();
        
        Object elementAjoute = controller.ObtenirElementSelectionne();
        if(elementAjoute == null){
            Object selection = controller.SelectionnerElement(positionInitiale);
            if(selection instanceof ElementSelectionnableDTO elementDTO){
            mettreAJourPanneauSelection(elementDTO);
            }
        }
        
        rafraichirVue();
    }
    private int convertInchesToPixels(int valeurEnPouces) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int dpi = toolkit.getScreenResolution();
        int valeurEnPixels = (int) Math.round(valeurEnPouces * dpi);
        return valeurEnPixels;
    }

    private int convertPixelsToInches(int valeurEnPixels) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int dpi = toolkit.getScreenResolution();
        int valeurEnPouces = (int) Math.round(valeurEnPixels / dpi);
        return valeurEnPouces;
    }
 
    private void supprimerMeubleButtonActionPerformed(java.awt.event.ActionEvent e) {
        boolean supprime = controller.SupprimerElementSelectionne();
        if (!supprime) {
            afficherErreur("pas supprime");
            return;
        }
        reinitialiserPanneauEdition();
        rafraichirVue();
    }
    
    
        // Valider si
    private void modifierElementButtonActionPerformed(java.awt.event.ActionEvent e) {

        if (longueurPiedsJText.getText().trim().isEmpty()
                || largeurPiedJText.getText().trim().isEmpty()
                || positionPiedXJText.getText().trim().isEmpty()
                || positionPiedYJText.getText().trim().isEmpty()) {
            return;
        }

        int longueur = 0;
        boolean longueurValide = true;
        try {
            longueur = Integer.parseInt(longueurPiedsJText.getText().trim());
        } catch (NumberFormatException ex) {
            longueurValide = false;
        }
        if (!longueurValide) {
            return;
        }

        int largeur = 0;
        boolean largeurValide = true;
        try {
            largeur = Integer.parseInt(largeurPiedJText.getText().trim());
        } catch (NumberFormatException ex) {
            largeurValide = false;

        }
        if (!largeurValide) {
            return;
        }

        int positionX = 0;
        boolean posXValide = true;
        try {
            positionX = Integer.parseInt(positionPiedXJText.getText().trim());
        } catch (NumberFormatException ex) {
            posXValide = false;
        }
        if (!posXValide) {
            return;
        }

        int positionY = 0;
        boolean posYValide = true;
        try {
            positionY = Integer.parseInt(positionPiedYJText.getText().trim());
        } catch (NumberFormatException ex) {
            posYValide = false;
        }
        if (!posYValide) {
            return;
        }

        int longueurConvertie = convertInchesToPixels(longueur);
        int largeurConvertie = convertInchesToPixels(largeur);
        Point positionConvertie = convertirPosition(positionX, positionY);

        Object elementSelectionne = controller.ObtenirElementSelectionne();
        MeubleDTO meubleDto = (elementSelectionne instanceof MeubleDTO) ? (MeubleDTO)elementSelectionne: null;

        if (meubleDto != null && MEUBLES_AVEC_DRAIN.contains(meubleDto.getNom())) {
            String diamText = diametreJText.getText().trim();
            int diametreDrainPixels = DIAMETRE_DRAIN_PIXELS;
            if (!diamText.isEmpty()) {
                boolean diametreValide = true;
                double diamInches = 0;
                try {
                    diamInches = Double.parseDouble(diamText);
                } catch (NumberFormatException ex) {
                    diametreValide = false;
                }
                if (diametreValide && diamInches > 0) {
                    int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
                    int newDiamPixels = (int) Math.round(diamInches * dpi);
                    diametreDrainPixels = newDiamPixels;
                    ElementSelectionnable element = controller.ObtenirElementSelectionneDirect();
                    if (element instanceof Domaine.Entite.MeubleAvecDrain meubleAvecDrain) {
                        meubleAvecDrain.setDiametreDrainPixels(newDiamPixels);
                    }
                }
            }
            if (largeurConvertie < diametreDrainPixels || longueurConvertie < diametreDrainPixels) {
                return;
            }
        }

        boolean modifie = controller.ModifierElementSelectionne(
                new Point(positionConvertie.x, positionConvertie.y - longueurConvertie),
                largeurConvertie, longueurConvertie);

        if (modifie) {
            Object maj = controller.ObtenirElementSelectionne();
            if(maj instanceof ElementSelectionnableDTO elemetDTO){
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
        int convertieLongueurMeuble = convertPixelsToInches(longueur);
        int convertieLargeurMeuble = convertPixelsToInches(largeur);
        longueurPiedsJText.setText(Integer.toString(convertieLongueurMeuble));
        largeurPiedJText.setText(Integer.toString(convertieLargeurMeuble));
        
        Point positionBase = element.getPosition();
        Point origine = controller.ObtenirOrigine();
        int valeurXConvertie = positionBase.x - origine.x;
        int valeurYConvertie = origine.y - (positionBase.y + longueur);
        positionPiedXJText.setText(Integer.toString(valeurXConvertie));
        positionYJText.setText(Integer.toString(valeurYConvertie));
        
        if (element instanceof MeubleDTO meuble && meuble.estAvecDrain()) {
            int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
            double diametreInches = (double) meuble.getDiametreDrainPixels() / dpi;
            diametreJText.setText(String.format("%.2f", diametreInches));
            diametreJText.setEnabled(true);
        } else {
            diametreJText.setText("");
            diametreJText.setEnabled(false);
        }
    }

    private void reinitialiserPanneauEdition() {
        longueurPiedsJText.setText("");
        largeurPiedJText.setText("");
        positionPiedXJText.setText("");
        positionPiedYJText.setText("");
    }

    private void afficherErreur(String message) {
        // TODO : A completer

    }

    private void rafraichirVue() {
        drawingPanel.repaint();
    }
    
    private void nouvellePieceButtonActionPerformed(java.awt.event.ActionEvent evt) { 
        String largeurText = largeurPieceJText.getText().trim();
        String longueurText = longueurPiedsJText.getText().trim();
        
        int largeur, longueur;
        
        if (largeurText.isEmpty() && longueurText.isEmpty()) {
            largeur = 10;
            longueur =10;
        } else if (largeurText.isEmpty() || longueurText.isEmpty()) {
            System.err.println(" Erreur : Veuillez entrer les deux dimensions ou laisser vides pour 10x10.");
            return;
        } else {
            try {
                largeur = Integer.parseInt(largeurText);
                longueur = Integer.parseInt(longueurText);
                if (largeur <= 0 || longueur <= 0) {
                    System.err.println( "Erreur : Dimensions doivent être > 0." );
                    return;
                }
            } catch (NumberFormatException e) {
                System.err.println("Erreur : Dimensions invalides.");
                return;
            }
        }
            int largeurPixels = convertInchesToPixels(largeur);
            int longueurPixels = convertInchesToPixels(longueur);
            int x = (drawingPanel.getWidth() - largeurPixels) / 2;
            int y = (drawingPanel.getHeight() - longueurPixels) / 2;
            
            String typePiece = (String) typePieceComboBox.getSelectedItem();
            java.awt.Polygon nouvelleForme;
            
            if ("Irregulière".equals(typePiece)) {
                int[] xPoints = { x, x + largeurPixels, x + largeurPixels, x + largeurPixels / 2, x };
                int[] yPoints = { y + longueurPixels / 3, y + longueurPixels / 3, y + longueurPixels, y + longueurPixels, y + longueurPixels / 3};
                nouvelleForme = new java.awt.Polygon(xPoints, yPoints, 5);
                
            } else {
                int[] xPoints = {x, x + largeurPixels, x + largeurPixels, x};
                int[] yPoints = {y, y, y + longueurPixels, y + longueurPixels};
                nouvelleForme = new java.awt.Polygon(xPoints, yPoints, 4);
            }
          
            controller = new HeatMyFloorController();
            controller.InitialiserPiece(nouvelleForme);
            //drawingPanel.setController(controller);
            
            reinitialiserPanneauEdition();
            largeurPieceJText.setText("");
            longueurPieceJText.setText("");
            rafraichirVue();
            
            System.out.println("Pièce '" + typePiece + "' (" + largeur + "x" + longueur + ") créée.");
    }
    
    private java.awt.Polygon creerFormeIrreguliere(int largeur, int longueur) {
        int x = (drawingPanel.getWidth() - largeur) / 2;
        int y = (drawingPanel.getHeight() - longueur) / 2;
        
        int[] xPoints = { x, x + largeur, x + largeur, x + largeur/2, x};
        int[] yPoints = { y, y, y + (longueur * 2/3), y + longueur, y + (longueur * 2/3) };
        
        return new java.awt.Polygon(xPoints, yPoints, 5);
    }

    private void diametreDrainButtonActionPerformed(java.awt.event.ActionEvent evt) {

    }
}
