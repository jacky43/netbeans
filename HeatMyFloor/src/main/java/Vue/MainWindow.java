package Vue;

import Domaine.DTO.MeubleDTO;
import Domaine.HeatMyFloorController;
import java.awt.Point;

@SuppressWarnings("serial")
public class MainWindow extends javax.swing.JFrame {
    
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
    private javax.swing.JTextField largeurJText;
    private javax.swing.JTextField longeurJText;
    private javax.swing.JLabel longueurJLabel;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> modelisationTypesBox;
    private javax.swing.JMenu nouvelleModelisationMenu;
    private javax.swing.JMenu outilsMenu1;
    private javax.swing.JMenuItem pieceIrreguliereItem;
    private javax.swing.JMenuItem pieceReguliereItem;
    private javax.swing.JLabel positionXJLabel;
    private javax.swing.JTextField positionXJText;
    private javax.swing.JLabel positionYJLabel;
    private javax.swing.JTextField positionYJText;
    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem sauvegarderItem;
    private javax.swing.JButton ajoutMeubleSDButton;
    private javax.swing.JButton supprimerMeubleButton;
    private javax.swing.JButton modifierMeubleButton;
    private javax.swing.JButton undoButton;
    private javax.swing.JComboBox meubleSansDrainBox;
    
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
        supprimerMeubleButton = new javax.swing.JButton();
        modifierMeubleButton = new javax.swing.JButton();
        longeurJText = new javax.swing.JTextField();
        largeurJText = new javax.swing.JTextField();
        positionXJText = new javax.swing.JTextField();
        positionYJText = new javax.swing.JTextField();
        longueurJLabel = new javax.swing.JLabel();
        largeurJLabel = new javax.swing.JLabel();
        positionXJLabel = new javax.swing.JLabel();
        positionYJLabel = new javax.swing.JLabel();
        meubleSansDrainBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.BorderLayout());

        buttonTopPanel.setPreferredSize(new java.awt.Dimension(1000, 50));

        modelisationTypesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modélisation Pièce", "Modélisation Fil" }));
        buttonTopPanel.add(modelisationTypesBox);
        
        meubleSansDrainBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARMOIRE", "PLACARD" }));
        buttonTopPanel.add(meubleSansDrainBox);

        undoButton.setText("Undo");
        
        buttonTopPanel.add(undoButton);
        redoButton.setText("Redo");
        buttonTopPanel.add(redoButton);
        
        // TODO : Contrôles pour la gestion du meuble
        ajoutMeubleSDButton.setText("Ajouter Meuble sans drain");
        ajoutMeubleSDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutMeubleSDButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(ajoutMeubleSDButton);
        supprimerMeubleButton.setText("Supprimer meuble");
        buttonTopPanel.add(supprimerMeubleButton);
        modifierMeubleButton.setText("Modifier meuble");
        buttonTopPanel.add(modifierMeubleButton);
        
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

        longueurJLabel.setText("Longueur");
        largeurJLabel.setText("Largeur");
        positionXJLabel.setText("Position X");
        positionYJLabel.setText("Position Y");
        positionXJText.setPreferredSize(new java.awt.Dimension(100, 22));
        positionYJText.setPreferredSize(new java.awt.Dimension(100, 22));
        longeurJText.setPreferredSize(new java.awt.Dimension(100, 22));
        largeurJText.setPreferredSize(new java.awt.Dimension(100, 22));

        choixLeftPanel = new javax.swing.JPanel();
        choixLeftPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixLeftPanel.setBackground(java.awt.Color.WHITE);
        choixLeftPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));

        javax.swing.GroupLayout choixLeftPanelLayout = new javax.swing.GroupLayout(choixLeftPanel);
        choixLeftPanel.setLayout(choixLeftPanelLayout);
        choixLeftPanelLayout.setHorizontalGroup(
            choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixLeftPanelLayout.createSequentialGroup()
                .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(choixLeftPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(positionYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(positionXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(largeurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(longeurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(positionYJLabel)
                                    .addComponent(positionXJLabel)))))
                    .addGroup(choixLeftPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(longueurJLabel))
                    .addGroup(choixLeftPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(largeurJLabel)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        choixLeftPanelLayout.setVerticalGroup(
            choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixLeftPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(longueurJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longeurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(largeurJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(largeurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(positionXJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionXJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(positionYJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionYJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST);
      
        drawingCenterPanel = new javax.swing.JPanel();
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(250, 350));
        drawingCenterPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawingCenterPanelMouseClicked(evt);
            }
        });
        java.awt.BorderLayout drawingCenterPanelLayout = new java.awt.BorderLayout();
        drawingCenterPanel.setLayout(drawingCenterPanelLayout);
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);
        
        choixRightPanel = new javax.swing.JPanel();
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixRightPanel.setBackground(java.awt.Color.WHITE);
        choixRightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY,1));
        
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
    
    private void drawingCenterPanelMouseClicked(java.awt.event.MouseEvent evt) 
    {
        Point positionSouris = evt.getPoint();
        positionXJText.setText(Integer.toString(positionSouris.x));
        positionYJText.setText(Integer.toString(positionSouris.y));
        controller.SelectionnerElement(positionSouris);
        rafraichirVue();
    } 
        
    private void ajoutMeubleSDButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Point positionPiece = controller.getPositionPiece();
        var typeMeuble = (String) meubleSansDrainBox.getSelectedItem();
        MeubleDTO dto = new MeubleDTO(
                new Point(100 + positionPiece.x, 100 + positionPiece.y), 
                50, 50, 
                typeMeuble
        );
        controller.AjouterMeuble(dto);
        rafraichirVue();
    }
    
    // TODO : Finaliser la conversion
    private Point convertPixelsToInches(Point mousePoint, double zoom)
    {
        return new Point(0, 0);
    }
    
    private void rafraichirVue(){
        drawingPanel.repaint();
    }
}
