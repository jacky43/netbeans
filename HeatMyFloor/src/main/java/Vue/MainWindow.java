package Vue;


import Domaine.HeatMyFloorController;

@SuppressWarnings("serial")
public class MainWindow extends javax.swing.JFrame {
    
    private javax.swing.JPanel InformationZoneBottomPanel;
    private javax.swing.JMenu aideMenu;
    private javax.swing.JPanel buttonTopPanel;
    private javax.swing.JPanel choixLeftPanel;
    private javax.swing.JPanel choixRightPanel;
    private javax.swing.JPanel drawingCenterPanel;
    private javax.swing.JMenu editionMenu;
    private javax.swing.JMenuItem exporterItem;
    private javax.swing.JMenu fichierMenu;
    private javax.swing.JMenuItem importerItem;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> modelisationTypesBox;
    private javax.swing.JMenu nouvelleModelisationMenu;
    private javax.swing.JMenu outilsMenu1;
    private javax.swing.JMenuItem pieceIrreguliereItem;
    private javax.swing.JMenuItem pieceReguliereItem;
    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem sauvegarderItem;
    private javax.swing.JButton undoButton;
    
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.BorderLayout());

        buttonTopPanel.setPreferredSize(new java.awt.Dimension(1000, 50));

        modelisationTypesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modélisation Pièce", "Modélisation Fil" }));
        buttonTopPanel.add(modelisationTypesBox);

        undoButton.setText("Undo");
        
        buttonTopPanel.add(undoButton);
        redoButton.setText("Redo");
        buttonTopPanel.add(redoButton);
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

      //LEFT PANEL
      choixLeftPanel = new javax.swing.JPanel(); /// me
      choixLeftPanel.setPreferredSize(new java.awt.Dimension(150, 350));
      choixLeftPanel.setBackground(java.awt.Color.WHITE);
      choixLeftPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));

        javax.swing.GroupLayout choixLeftPanelLayout = new javax.swing.GroupLayout(choixLeftPanel);
        choixLeftPanel.setLayout(choixLeftPanelLayout);
        choixLeftPanelLayout.setHorizontalGroup(
            choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        choixLeftPanelLayout.setVerticalGroup(
            choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );

      mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST); //used to be east
      
        //CENTER PANEL
        drawingCenterPanel = new javax.swing.JPanel(); //// me
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(250, 350));
        java.awt.BorderLayout drawingCenterPanelLayout = new java.awt.BorderLayout();
        drawingCenterPanel.setLayout(drawingCenterPanelLayout);
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);
        
        //RIGHT PANEL
        choixRightPanel = new javax.swing.JPanel(); /// me
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixRightPanel.setBackground(java.awt.Color.WHITE);
        choixRightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY,1)); ///me
        
        javax.swing.GroupLayout choixRightPanelLayout = new javax.swing.GroupLayout(choixRightPanel);
        choixRightPanel.setLayout(choixRightPanelLayout);
        choixRightPanelLayout.setHorizontalGroup(
            choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        choixRightPanelLayout.setVerticalGroup(
            choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );

        mainPanel.add(choixRightPanel, java.awt.BorderLayout.EAST);  ///used to be west

        
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
}
