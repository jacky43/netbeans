package Vue;

import Domaine.DTO.MeubleDTO;
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
    private javax.swing.JTextField largeurJText;
    private javax.swing.JTextField longueurJText;
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
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JButton zoomResetButton;
    private javax.swing.JLabel zoomLabel;
    private javax.swing.JComboBox<String> meubleSansDrainBox;
    private javax.swing.JLabel diametreJLabel;
    private javax.swing.JTextField diametreJText;
    private static int DIAMETRE_DRAIN_PIXELS = 10;

    private final int LONGUEUR_INITIALE_MEUBLE = 1;
    private final int LARGEUR_INITIALE_MEUBLE = 1;

    // Variables pour le zoom
    private double zoomFactor = 1.0;
    private final double ZOOM_INCREMENT = 0.1;
    private final double MIN_ZOOM = 0.1;
    private final double MAX_ZOOM = 5.0;

    // Offset pour le zoom centré sur la souris
    private double panOffsetX = 0;
    private double panOffsetY = 0;

    // Position de la dernière souris pour le zoom
    private Point lastMousePosition = null;

    private void diametreJTextActionPerformed(java.awt.event.ActionEvent evt) {

    }

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
        longueurJText = new javax.swing.JTextField();
        largeurJText = new javax.swing.JTextField();
        positionXJText = new javax.swing.JTextField();
        positionYJText = new javax.swing.JTextField();
        longueurJLabel = new javax.swing.JLabel();
        largeurJLabel = new javax.swing.JLabel();
        positionXJLabel = new javax.swing.JLabel();
        positionYJLabel = new javax.swing.JLabel();
        meubleSansDrainBox = new javax.swing.JComboBox<>();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        zoomResetButton = new javax.swing.JButton();
        zoomLabel = new javax.swing.JLabel();
        diametreJText = new javax.swing.JTextField();
        diametreJLabel = new javax.swing.JLabel();

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
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(zoomInButton);

        zoomOutButton.setText("Zoom -");
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(zoomOutButton);

        zoomResetButton.setText("Zoom 100%");
        zoomResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomResetButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(zoomResetButton);

        zoomLabel.setText("100%");
        buttonTopPanel.add(zoomLabel);

        ajoutMeubleSDButton.setText("Ajouter Meuble");
        ajoutMeubleSDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutMeubleSDButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(ajoutMeubleSDButton);

        supprimerMeubleButton.setText("Supprimer meuble");
        supprimerMeubleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerMeubleButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(supprimerMeubleButton);

        modifierMeubleButton.setText("Modifier meuble");
        modifierMeubleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifierMeubleButtonActionPerformed(evt);
            }
        });
        buttonTopPanel.add(modifierMeubleButton);

        diametreJText.setPreferredSize(new java.awt.Dimension(100, 22));
        diametreJText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diametreJTextActionPerformed(evt);
            }
        });
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

        drawingPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                drawingPanelMouseWheelMoved(evt);
            }
        });

        longueurJLabel.setText("Longueur");
        largeurJLabel.setText("Largeur");
        positionXJLabel.setText("Position X");
        positionYJLabel.setText("Position Y");
        positionXJText.setPreferredSize(new java.awt.Dimension(100, 22));
        positionYJText.setPreferredSize(new java.awt.Dimension(100, 22));
        longueurJText.setPreferredSize(new java.awt.Dimension(100, 22));
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
                                                        .addComponent(longueurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(diametreJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                                .addComponent(largeurJLabel))
                                        .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(diametreJLabel)))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

        choixLeftPanelLayout.setVerticalGroup(
                choixLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(choixLeftPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(longueurJLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(longueurJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addGap(24, 24, 24)
                                .addComponent(diametreJLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diametreJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        mainPanel.add(choixLeftPanel, java.awt.BorderLayout.WEST);

        drawingCenterPanel = new javax.swing.JPanel();
        drawingCenterPanel.setPreferredSize(new java.awt.Dimension(250, 350));
        java.awt.BorderLayout drawingCenterPanelLayout = new java.awt.BorderLayout();
        drawingCenterPanel.setLayout(drawingCenterPanelLayout);
        drawingCenterPanel.add(drawingPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(drawingCenterPanel, java.awt.BorderLayout.CENTER);

        choixRightPanel = new javax.swing.JPanel();
        choixRightPanel.setPreferredSize(new java.awt.Dimension(150, 350));
        choixRightPanel.setBackground(java.awt.Color.WHITE);
        choixRightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));

        javax.swing.GroupLayout choixRightPanelLayout = new javax.swing.GroupLayout(choixRightPanel);
        choixRightPanel.setLayout(choixRightPanelLayout);
        choixRightPanelLayout.setHorizontalGroup(
                choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );
        choixRightPanelLayout.setVerticalGroup(
                choixRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 350, Short.MAX_VALUE)
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
        if (zoomFactor < MAX_ZOOM) {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            zoomAt(center, ZOOM_INCREMENT);
        }
    }

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (zoomFactor > MIN_ZOOM) {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            zoomAt(center, -ZOOM_INCREMENT);
        }
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
        newZoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, newZoom));

        if (newZoom == zoomFactor) {
            return;
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

        MeubleDTO selection = controller.SelectionnerElement(positionMonde);
        if (selection != null) {
            mettreAJourPanneauSelection(selection);
        } else {
            reinitialiserPanneauEdition();
        }
        rafraichirVue();
    }

    private void drawingPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {

        int rotation = evt.getWheelRotation();
        Point mousePos = evt.getPoint();

        if (rotation < 0) {
            if (zoomFactor < MAX_ZOOM) {
                zoomAt(mousePos, ZOOM_INCREMENT);
            }
        } else {
            if (zoomFactor > MIN_ZOOM) {
                zoomAt(mousePos, -ZOOM_INCREMENT);
            }
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

        MeubleDTO selection = controller.SelectionnerElement(positionMeuble);
        if (selection != null) {
            mettreAJourPanneauSelection(selection);
        }

        rafraichirVue();
    }

    private void ajoutMeubleSDButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String typeMeuble = (String) meubleSansDrainBox.getSelectedItem();

        // TODO -  : Considérer le diamètre du drain pour les cas avec drain
        ajouterMeuble(typeMeuble);
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
        boolean supprime = controller.SupprimerMeubleSelectionne();
        if (!supprime) {
            afficherErreur("pas supprime");
            return;
        }
        reinitialiserPanneauEdition();
        rafraichirVue();
    }

    // TODO - Staelle : Considérer les cas avec drain et sans drain et le diamètre du drain
        // Valider si
    private void modifierMeubleButtonActionPerformed(java.awt.event.ActionEvent e) {

        if (longueurJText.getText().trim().isEmpty()
                || largeurJText.getText().trim().isEmpty()
                || positionXJText.getText().trim().isEmpty()
                || positionYJText.getText().trim().isEmpty()) {
            return;
        }

        int longueur = 0;
        boolean longueurValide = true;
        try {
            longueur = Integer.parseInt(longueurJText.getText().trim());
        } catch (NumberFormatException ex) {
            longueurValide = false;
        }
        if (!longueurValide) {
            return;
        }

        int largeur = 0;
        boolean largeurValide = true;
        try {
            largeur = Integer.parseInt(largeurJText.getText().trim());
        } catch (NumberFormatException ex) {
            largeurValide = false;

        }
        if (!largeurValide) {
            return;
        }

        int positionX = 0;
        boolean posXValide = true;
        try {
            positionX = Integer.parseInt(positionXJText.getText().trim());
        } catch (NumberFormatException ex) {
            posXValide = false;
        }
        if (!posXValide) {
            return;
        }

        int positionY = 0;
        boolean posYValide = true;
        try {
            positionY = Integer.parseInt(positionYJText.getText().trim());
        } catch (NumberFormatException ex) {
            posYValide = false;
        }
        if (!posYValide) {
            return;
        }

        int longueurConvertie = convertInchesToPixels(longueur);
        int largeurConvertie = convertInchesToPixels(largeur);
        Point positionConvertie = convertirPosition(positionX, positionY);

        MeubleDTO meubleSelectionne = controller.ObtenirmeubleSelectionne();

        if (meubleSelectionne != null && MEUBLES_AVEC_DRAIN.contains(meubleSelectionne.getNom())) {
            String diamText = diametreJText.getText().trim();
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

                    Domaine.Entite.ElementSelectionnable element = controller.ObtenirElementSelectionneDirect();
                    if (element instanceof Domaine.Entite.MeubleAvecDrain meubleAvecDrain) {
                        meubleAvecDrain.setDiametreDrainPixels(newDiamPixels);
                    }
                }
            }
        }

        if (meubleSelectionne != null && MEUBLES_AVEC_DRAIN.contains(meubleSelectionne.getNom())) {
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
                    diametreDrainPixels = (int) Math.round(diamInches * dpi);
                }
            }
            if (largeurConvertie < diametreDrainPixels || longueurConvertie < diametreDrainPixels) {
                return;
            }
        }

        boolean modifie = controller.ModifierMeubleSelectionne(
                new Point(positionConvertie.x, positionConvertie.y - longueurConvertie),
                largeurConvertie, longueurConvertie);

        if (!modifie) {
            MeubleDTO maj = controller.ObtenirmeubleSelectionne();
            mettreAJourPanneauSelection(maj);
            rafraichirVue();
        }
    }

    private Point convertirPosition(int x, int y) {
        Point origine = controller.ObtenirOrigine();
        return new Point(origine.x + x, origine.y - y);
    }

    private void mettreAJourPanneauSelection(MeubleDTO meuble) {
        if (meuble == null) {
            return;
        }
        int longueurMeuble = meuble.getLongueur();
        int largeurMeuble = meuble.getLargeur();
        int convertieLongueurMeuble = convertPixelsToInches(longueurMeuble);
        int convertieLargeurMeuble = convertPixelsToInches(largeurMeuble);
        longueurJText.setText(Integer.toString(convertieLongueurMeuble));
        largeurJText.setText(Integer.toString(convertieLargeurMeuble));
        Point positionBaseMeuble = meuble.getPosition();
        Point origine = controller.ObtenirOrigine();
        int valeurXConvertie = positionBaseMeuble.x - origine.x;
        int valeurYConvertie = origine.y - (positionBaseMeuble.y + longueurMeuble);
        positionXJText.setText(Integer.toString(valeurXConvertie));
        positionYJText.setText(Integer.toString(valeurYConvertie));

        if (meuble.estAvecDrain()) {
            int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
            double diametreInches = (double) meuble.getDiametreDrainPixels() / dpi;
            diametreJText.setText(String.format("%.2f", diametreInches));
        } else {
            diametreJText.setText("");
        }
    }

    private void reinitialiserPanneauEdition() {
        longueurJText.setText("");
        largeurJText.setText("");
        positionXJText.setText("");
        positionYJText.setText("");
    }

    private void afficherErreur(String message) {
        // TODO : A completer

    }

    private void rafraichirVue() {
        drawingPanel.repaint();
    }

    private void diametreDrainButtonActionPerformed(java.awt.event.ActionEvent evt) {

    }
}
