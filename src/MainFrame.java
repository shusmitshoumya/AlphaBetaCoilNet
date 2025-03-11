import java.awt.EventQueue; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.awt.SystemColor;

public class MainFrame {

	private JFrame frmProteinSecondaryStructure;
	private JButton browseBtn;
	private String temp;
	
	private static final Map<Character, double[]> PROPENSITY_MAP = new HashMap<>();
    
    static {
        PROPENSITY_MAP.put('A', new double[]{1.42, 0.83, 0.66}); // Alanine
        PROPENSITY_MAP.put('C', new double[]{0.77, 1.19, 1.34}); // Cysteine
        PROPENSITY_MAP.put('D', new double[]{1.01, 0.54, 1.46}); // Aspartic Acid
        PROPENSITY_MAP.put('E', new double[]{1.51, 0.37, 0.74}); // Glutamic Acid
        PROPENSITY_MAP.put('F', new double[]{1.13, 1.38, 0.59}); // Phenylalanine
        PROPENSITY_MAP.put('G', new double[]{0.57, 0.75, 1.56}); // Glycine
        PROPENSITY_MAP.put('H', new double[]{1.00, 0.87, 0.95}); // Histidine
        PROPENSITY_MAP.put('I', new double[]{1.08, 1.60, 0.47}); // Isoleucine
        PROPENSITY_MAP.put('K', new double[]{1.16, 0.74, 1.01}); // Lysine
        PROPENSITY_MAP.put('L', new double[]{1.21, 1.30, 0.59}); // Leucine
        PROPENSITY_MAP.put('M', new double[]{1.45, 1.05, 0.60}); // Methionine (Start codon)
        PROPENSITY_MAP.put('N', new double[]{0.67, 0.89, 1.56}); // Asparagine
        PROPENSITY_MAP.put('P', new double[]{0.57, 0.55, 1.52}); // Proline
        PROPENSITY_MAP.put('Q', new double[]{1.11, 1.10, 0.98}); // Glutamine
        PROPENSITY_MAP.put('R', new double[]{0.98, 0.93, 0.95}); // Arginine
        PROPENSITY_MAP.put('S', new double[]{0.77, 0.75, 1.43}); // Serine
        PROPENSITY_MAP.put('T', new double[]{0.83, 1.19, 1.20}); // Threonine
        PROPENSITY_MAP.put('V', new double[]{1.06, 1.70, 0.50}); // Valine
        PROPENSITY_MAP.put('W', new double[]{1.08, 1.37, 0.66}); // Tryptophan
        PROPENSITY_MAP.put('Y', new double[]{0.69, 1.47, 0.68}); // Tyrosine
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmProteinSecondaryStructure.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmProteinSecondaryStructure = new JFrame();
		frmProteinSecondaryStructure.getContentPane().setBackground(new Color(0, 48, 146));
		frmProteinSecondaryStructure.setResizable(false);
		frmProteinSecondaryStructure.getContentPane().setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 16));
		frmProteinSecondaryStructure.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\SHOUMYA\\eclipse-workspace\\ProteinStructurePrediction\\images\\molecule.png"));
		frmProteinSecondaryStructure.setTitle("AlphaBetaCoilNet");
		frmProteinSecondaryStructure.setBounds(100, 100, 1200, 650);
		frmProteinSecondaryStructure.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frmProteinSecondaryStructure.getContentPane().setLayout(null);
		
		// Load background image
	    ImageIcon bgIcon = new ImageIcon("C:\\Users\\SHOUMYA\\eclipse-workspace\\ProteinStructurePrediction\\images\\bgimageedit.jpg"); // Change path
	    Image bgImage = bgIcon.getImage();

	    // Custom JPanel with background
	    JPanel bgPanel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    
	    bgPanel.setLayout(null);
	    frmProteinSecondaryStructure.setContentPane(bgPanel); // Set the panel as content pane

		JLabel header = new JLabel("AlphaBetaCoilNet");
		header.setBounds(10, 36, 1166, 93);
		header.setForeground(new Color(245, 255, 250));
		header.setFont(new Font("Microsoft YaHei", Font.BOLD, 50));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		frmProteinSecondaryStructure.getContentPane().add(header);
		
		JTextArea inputSeq = new JTextArea();
		inputSeq.setColumns(10);
		inputSeq.setBackground(new Color(255, 255, 255));
		inputSeq.setRows(3); // Increase value for more room to scroll
		inputSeq.setForeground(new Color(0, 0, 128));
		inputSeq.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 17));
		inputSeq.setLineWrap(true);  // Enable line wrapping
		inputSeq.setWrapStyleWord(true); // Wrap at word boundaries
		((AbstractDocument) inputSeq.getDocument()).setDocumentFilter(new UppercaseFilter());
		((AbstractDocument) inputSeq.getDocument()).setDocumentFilter(new AminoAcidDocumentFilter());
		inputSeq.setBounds(64, 279, 964, 34);
//		frmProteinSecondaryStructure.getContentPane().add(inputSeq);
		
		JScrollPane scrollPane = new JScrollPane(inputSeq);
		scrollPane.setBounds(64, 279, 964, 34); // Set the size for the scrollable area
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmProteinSecondaryStructure.getContentPane().add(scrollPane);
		
		JLabel errorMsg = new JLabel("");
		errorMsg.setForeground(new Color(252, 242, 219));
		errorMsg.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 18));
		errorMsg.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg.setBounds(64, 388, 1059, 23);
		bgPanel.add(errorMsg);
		
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.addActionListener(e -> inputSeq.copy());
		popupMenu.add(copyItem);

		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.addActionListener(e -> inputSeq.paste());
		popupMenu.add(pasteItem);

		JMenuItem cutItem = new JMenuItem("Cut");
		cutItem.addActionListener(e -> inputSeq.cut());
		popupMenu.add(cutItem);

		// Add mouse listener to show popup menu on right-click
		inputSeq.setComponentPopupMenu(popupMenu);
		
		JLabel orLabel = new JLabel("or");
		orLabel.setForeground(new Color(255, 255, 255));
		orLabel.setBounds(64, 336, 45, 25);
		orLabel.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 18));
		frmProteinSecondaryStructure.getContentPane().add(orLabel);
		
		JLabel filenameLabel = new JLabel("");
		filenameLabel.setForeground(new Color(248, 248, 255));
		filenameLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 16));
		filenameLabel.setBounds(299, 338, 364, 23);
		frmProteinSecondaryStructure.getContentPane().add(filenameLabel);
		
		//Browse button
		browseBtn = new JButton("Upload FASTA file");
		browseBtn.setBackground(new Color(0, 135, 158));
		browseBtn.setForeground(Color.WHITE);
		browseBtn.setFocusable(false);
		browseBtn.setBounds(96, 332, 160, 29);
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == browseBtn) {
					
					JFileChooser fileChooser = new JFileChooser();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
						    "FASTA Files", "fasta", "fna", "fa", "fas", "ffn", "faa", "mpfa", "frn");
					fileChooser.setFileFilter(filter);
					
					fileChooser.setCurrentDirectory(new File("C:\\Users\\SHOUMYA\\eclipse-workspace\\ProteinStructurePrediction\\Sample Sequence"));
					
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						File filepath = new File(fileChooser.getSelectedFile().getAbsolutePath());
						
						File file = fileChooser.getSelectedFile();
						
						String filename = file.getName();
						
						filenameLabel.setText(filename.toString());
						System.out.println(filepath);
						
						String fastaSeq = readFastaFile(filepath);
						inputSeq.setText(fastaSeq);
					}
				}
			}
		});
		browseBtn.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 14));
		frmProteinSecondaryStructure.getContentPane().add(browseBtn);
		
		JLabel outputLabel = new JLabel("Predicted Sequence:");
		outputLabel.setForeground(new Color(255, 255, 255));
		outputLabel.setBounds(64, 413, 298, 47);
		outputLabel.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 28));
		frmProteinSecondaryStructure.getContentPane().add(outputLabel);
		
		JLabel helixLabel = new JLabel("α-Helix");
		helixLabel.setForeground(new Color(255, 255, 255));
		helixLabel.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		helixLabel.setBounds(695, 337, 63, 25);
		frmProteinSecondaryStructure.getContentPane().add(helixLabel);
		
		JLabel sheetLabel = new JLabel("β-Sheet");
		sheetLabel.setForeground(new Color(255, 255, 255));
		sheetLabel.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		sheetLabel.setBounds(823, 337, 72, 25);
		frmProteinSecondaryStructure.getContentPane().add(sheetLabel);
		
		JLabel coilLabel = new JLabel("Coil");
		coilLabel.setForeground(new Color(255, 255, 255));
		coilLabel.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		coilLabel.setBounds(952, 338, 45, 22);
		frmProteinSecondaryStructure.getContentPane().add(coilLabel);
		
		Panel helixColor = new Panel();
		helixColor.setBackground(new Color(13, 124, 102));
		helixColor.setBounds(764, 336, 25, 25);
		frmProteinSecondaryStructure.getContentPane().add(helixColor);
		
		Panel sheetColor = new Panel();
		sheetColor.setBackground(new Color(184, 0, 31));
		sheetColor.setBounds(901, 336, 25, 25);
		frmProteinSecondaryStructure.getContentPane().add(sheetColor);
		
		Panel coilColor = new Panel();
		coilColor.setBackground(new Color(61, 59, 243));
		coilColor.setBounds(1003, 336, 25, 25);
		frmProteinSecondaryStructure.getContentPane().add(coilColor);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBackground(new Color(255, 255, 255));
		outputPanel.setBounds(64, 470, 1059, 93);
		frmProteinSecondaryStructure.getContentPane().add(outputPanel);
		outputPanel.setLayout(null);
		
		JLabel outputSeq = new JLabel("");
		outputSeq.setBounds(0, 0, 1059, 93);
		outputPanel.add(outputSeq);
		outputSeq.setForeground(new Color(255, 255, 255));
		outputSeq.setBackground(new Color(255, 242, 219));
		outputSeq.setHorizontalAlignment(SwingConstants.LEFT);
		outputSeq.setVerticalAlignment(SwingConstants.TOP);
		outputSeq.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 17)); // Update : Panel size boro korsi
		
		JButton predictBtn = new JButton("Predict");
		predictBtn.setForeground(Color.WHITE);
		predictBtn.setBackground(new Color(0, 135, 158));
		predictBtn.setFocusable(false);
		predictBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seq = inputSeq.getText().trim().toUpperCase();
				
				if(seq.isEmpty()) {
					errorMsg.setText("Please enter your sequence.");
				}
//				else if(!seq.matches("[ACDEFGHIKLMNPQRSTVWY]+")) {
//					errorMsg.setText("Invalid Sequence.");
//				}
				else {
					temp = predictStructure(seq);
//					outputSeq.setText(predictedSeq);
					System.out.println(temp);
					String finalSeq = coloredSequence(seq, temp);
					outputSeq.setText(finalSeq);
					errorMsg.setText("");
//					calcPercentage(temp);
				}
			}
		});
		predictBtn.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 16));
		predictBtn.setBounds(1038, 279, 85, 34);
		frmProteinSecondaryStructure.getContentPane().add(predictBtn);
		
		JLabel lblProteinStructurePrediction = new JLabel("Protein Structure Prediction");
		lblProteinStructurePrediction.setHorizontalAlignment(SwingConstants.CENTER);
		lblProteinStructurePrediction.setForeground(new Color(255, 171, 91));
		lblProteinStructurePrediction.setFont(new Font("Microsoft YaHei", Font.BOLD, 45));
		lblProteinStructurePrediction.setBounds(10, 115, 1166, 93);
		frmProteinSecondaryStructure.getContentPane().add(lblProteinStructurePrediction);
		
		JLabel lblDevelopedByShoumya = new JLabel("Developed by Shusmit Shoumya Rudra\r\n");
		lblDevelopedByShoumya.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevelopedByShoumya.setForeground(new Color(255, 242, 219));
		lblDevelopedByShoumya.setFont(new Font("Microsoft YaHei", Font.PLAIN, 30));
		lblDevelopedByShoumya.setBounds(10, 172, 1166, 93);
		frmProteinSecondaryStructure.getContentPane().add(lblDevelopedByShoumya);
		
		// Use "Enter" key to input the sequence
		inputSeq.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "pressEnter");
		inputSeq.getActionMap().put("pressEnter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				predictBtn.doClick(); // Simulate a button click
			}
		});
	}
	
	private String predictStructure(String seq) {
		int windowSize = 6; // Typical window size for Chou-Fasman
        StringBuilder structure = new StringBuilder();

        for (int i = 0; i < seq.length(); i++) {
            if (i + windowSize > seq.length()) {
                structure.append("C"); // Assign default Coil for remaining residues
                continue;
            }

            double helixSum = 0, sheetSum = 0, coilSum = 0;

            // Calculate average propensity in the window
            for (int j = i; j < i + windowSize; j++) {
                char aa = seq.charAt(j);
                if (PROPENSITY_MAP.containsKey(aa)) {
                    double[] propensities = PROPENSITY_MAP.get(aa);
                    helixSum += propensities[0];
                    sheetSum += propensities[1];
                    coilSum += propensities[2];
                }
            }

            double helixAvg = helixSum / windowSize;
            double sheetAvg = sheetSum / windowSize;
            double coilAvg = coilSum / windowSize;
            
            if (helixAvg > sheetAvg && helixAvg > coilAvg) {
                structure.append("H");
            } 
            else if (sheetAvg > helixAvg && sheetAvg > coilAvg) {
                structure.append("E");
            }
            else {
                structure.append("C");
            }
        }
        return structure.toString();
    }
	
	private String coloredSequence(String input, String tempSeq) {
		StringBuilder formattedText = new StringBuilder("<html>");
		for (int i = 0; i < input.length(); i++) {
		    char aa = input.charAt(i);
		    char structure = tempSeq.charAt(i);
		    
		    String color;
		    if (structure == 'H') color = "#0D7C66"; // Green
		    else if (structure == 'E') color = "#B8001F"; // Red
		    else color = "#3D3BF3"; // Blue
		    
		    formattedText.append("<font color='").append(color).append("'>").append(aa).append("</font> ");
		}
		formattedText.append("</html>");
		
		return formattedText.toString();
	}
	
	private String readFastaFile(File file) {
		StringBuilder sequence = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith(">")) {
					sequence.append(line.trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sequence.toString();
	}
	
	// For Testing
//	private void calcPercentage(String temp) {
//		int hCount = 0, eCount = 0, cCount = 0;
//	    
//	    for (char c : temp.toCharArray()) {
//	        if (c == 'H') {
//	        	hCount++;
//	        }
//	        else if (c == 'E') {
//	        	eCount++;
//	        }
//	        else {
//	        	cCount++;
//	        }
//	    }
//	    
//	    int total = temp.length();
//	    double hPercentage = (hCount * 100.0) / total;
//	    double ePercentage = (eCount * 100.0) / total;
//	    double cPercentage = (cCount * 100.0) / total;
//	    
//	    System.out.printf("Helix: %.2f%%, Sheet: %.2f%%, Coil: %.2f%%%n", hPercentage, ePercentage, cPercentage);
//	}
}
