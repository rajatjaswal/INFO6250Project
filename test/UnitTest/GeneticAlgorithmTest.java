package UnitTest;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.junit.Test;

import GA.FitnessFunction;
import GA.Genotype;
import GA.IndividualImage;
import GA.Mutation;
import GA.Populate;
import ImageProcess.ImageJPanel;
import ImageProcess.ProcessImage;
import ImageProcess.RGB;

/**
 * Test methods for GeneticAlgorithm.
 */
public class GeneticAlgorithmTest {
	
	@Test
    public void testFitness1() throws Exception {
		// Read two files
		File filex,file1,file2;
		BufferedImage oImage=null,image1 = null,image2=null;
		try {
			// Read from a file
			filex= new File("testImages/human.jpg");
			file1 = new File("testImages/1.png");
			file2 = new File("testImages/51.png");
			oImage= ImageIO.read(filex);
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);

		} catch (IOException e) {
			System.out.println("File not read!!");
			return;
		}
		
		RGB[][] originalRGB=ProcessImage.readValuesAsPixels(oImage);
		long fitness1 = FitnessFunction.evaluateFitnessMain(image1, originalRGB);
		long fitness2 = FitnessFunction.evaluateFitnessMain(image2, originalRGB);
		assert(fitness1 > fitness2);
	}
	@Test
    public void testFitness2() throws Exception {
		// Read two files
		File filex,file1,file2;
		BufferedImage oImage=null,image1 = null,image2=null;
		try {
			// Read from a file
			filex= new File("testImages/human.jpg");
			file1 = new File("testImages/753.png");
			file2 = new File("testImages/51.png");
			oImage= ImageIO.read(filex);
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);

		} catch (IOException e) {
			System.out.println("File not read!!");
			return;
		}
		
		RGB[][] originalRGB=ProcessImage.readValuesAsPixels(oImage);
		long fitness1 = FitnessFunction.evaluateFitnessMain(image1, originalRGB);
		long fitness2 = FitnessFunction.evaluateFitnessMain(image2, originalRGB);
		assert(fitness1 < fitness2);
	}
	
	@Test
	public void testMutation() throws Exception{
		List<Genotype> genotypeList = new ArrayList<>();
		File file = new File("testImages/51.png");
		BufferedImage bi = ImageIO.read(file);
		for(int i=0;i<1800;i++){
			genotypeList.add(Mutation.getRandomGenes(32, bi, "asgwvhf22qwertyuioplasdnjd"));	
		}
		RGB[][] originalRGB=ProcessImage.readValuesAsPixels(bi);
		IndividualImage image= new IndividualImage(genotypeList);
		
		FitnessFunction.evaluateFitness(image, bi, originalRGB);
		long fitness1 =image.getFitness();
		Mutation.mutate(genotypeList, 32, bi, "asgwvhf22qwertyuioplasdnjd");
		FitnessFunction.evaluateFitness(image, bi, originalRGB);
		long fitness2 =image.getFitness();
		assert(fitness1 > fitness2);
	}
	@Test
	public void testPopulation1() throws Exception{
		int sizeOfPopulate = 0;
		File file = new File("testImages/51.png");
		BufferedImage bi = ImageIO.read(file);	
		sizeOfPopulate = Populate.initPool(450, 200, 32, bi, "sss").size();
		assert(sizeOfPopulate>0);
	}
	@Test
	public void testGenotype() throws Exception{
		File file = new File("testImages/51.png");
		BufferedImage bi = ImageIO.read(file);
		Genotype gene=Mutation.getRandomGenes(32, bi, "abcd");
		if(gene.getCharacter()!='\0' && gene.getColor()!=null && gene.getSize() >= 0 && gene.getY() >=0) {
			assert(true);
		}else {
			assert(false);
		}
	}
	@Test
	public void testRGB() throws Exception{
		RGB rgb1=new RGB(255,0,255);
		RGB rgb2=new RGB(0,245,168);
		int diff=rgb1.difference(rgb2);
		assert(diff>0);
	}
	@Test
	public void testCrossover() throws Exception{
		// Read two files
		File filex,file1,file2;
		BufferedImage oImage=null,image1 = null,image2=null;
		try {
			// Read from a file
			filex= new File("testImages/human.jpg");
			file1 = new File("testImages/51.png");
			file2 = new File("testImages/52.png");
			oImage= ImageIO.read(filex);
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);

		} catch (IOException e) {
			System.out.println("File not read!!");
			return;
		}
		List<Genotype> genes1 = new ArrayList<>();
		for(int i=0;i<1800;i++){
			genes1.add(Mutation.getRandomGenes(32, oImage, "asgwvhf22qwertyuioplasdnjd"));	
		}
		IndividualImage imageData1=new IndividualImage(genes1);
		
		List<Genotype> genes2 = new ArrayList<>();
		for(int i=0;i<1800;i++){
			genes2.add(Mutation.getRandomGenes(32, oImage, "asgwvhf22qwertyuioplasdnjd"));	
		}
		IndividualImage imageData2=new IndividualImage(genes2);
		
		RGB[][] originalRGB=ProcessImage.readValuesAsPixels(oImage);
		
		IndividualImage imageDataChild=Mutation.crossOver(imageData1, imageData2, 1800, 1, 32, oImage, "asgwvhf22qwertyuioplasdnjd");
		BufferedImage imageChild=ProcessImage.getBufferedImage(imageDataChild, oImage);
		long fitness1=FitnessFunction.evaluateFitnessMain(image1, originalRGB);
		long fitness2=FitnessFunction.evaluateFitnessMain(image2, originalRGB);
		long fitness3=FitnessFunction.evaluateFitnessMain(imageChild, originalRGB);

		assert(fitness3 !=fitness2 && fitness3 !=fitness1);
		
	}
}
