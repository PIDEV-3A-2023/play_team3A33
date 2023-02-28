<?php

namespace App\Controller;

use App\Entity\Abonnement;
use App\Form\AbonnementType;
use App\Repository\AbonnementRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

class AbonnementController extends AbstractController
{
    #[Route('/abonnement', name: 'app_abonnement')]
    public function index(): Response
    {
        return $this->render('admin.html.twig', [
            'controller_name' => 'AbonnementController',
        ]);
    }
    #[Route('/add_abonnement', name: 'add_abonnement')]

    public function Add(Request  $request , ManagerRegistry $doctrine ,SluggerInterface $slugger) : Response {

        $Abonnement =  new Abonnement() ;
        $form =  $this->createForm(AbonnementType::class,$Abonnement) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            $brochureFile = $form->get('image')->getData();
            //$file =$Abonnement->getImage();
            $originalFilename = pathinfo($brochureFile->getClientOriginalName(), PATHINFO_FILENAME);
            //$uploads_directory = $this->getParameter('upload_directory');
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $safeFilename.'-'.uniqid().'.'.$brochureFile->guessExtension();
            //$fileName = md5(uniqid()).'.'.$file->guessExtension();
            $brochureFile->move(
                $this->getParameter('upload_directory'),
                $newFilename
            );
            $Abonnement->setImage(($newFilename));
            $Abonnement = $form->getData();
            $em= $doctrine->getManager() ;
            $em->persist($Abonnement);
            $em->flush();
            return $this ->redirectToRoute('add_abonnement') ;
        }
        return $this->render('abonnement/addabonnements.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_ab', name: 'afficher_ab')]
    public function AfficheAbonnement (AbonnementRepository $repo   ): Response
    {
        //$repo=$this ->getDoctrine()->getRepository(Abonnement::class) ;
        $Abonnement=$repo->findAll() ;
        return $this->render('abonnement/index.html.twig' , [
            'Abonnement' => $Abonnement ,
            'ajoutA' => $Abonnement
        ]) ;
    }

    #[Route('/delete_ab/{id}', name: 'delete_ab')]
    public function Delete($id,AbonnementRepository $repository , ManagerRegistry $doctrine) : Response {
        $Abonnement=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Abonnement);
        $em->flush();
        return $this->redirectToRoute("afficher_ab") ;

    }
    #[Route('/update_ab/{id}', name: 'update_ab')]
    function update(AbonnementRepository $repo,$id,Request $request , ManagerRegistry $doctrine){
        $Abonnement = $repo->find($id) ;
        $form=$this->createForm(AbonnementType::class,$Abonnement) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){

            $Abonnement = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_ab') ;
        }
        return $this->render('abonnement/updateabonnements.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }
}
