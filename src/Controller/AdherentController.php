<?php

namespace App\Controller;

use App\Entity\Adherent;
use App\Form\AdherentType;
use App\Repository\AdherentRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AdherentController extends AbstractController
{
    #[Route('/adherent', name: 'app_adherent')]
    public function index(): Response
    {
        return $this->render('base.html.twig', [
            'controller_name' => 'AdherentController',
        ]);
    }
    #[Route('/add_Adherent', name: 'add_Adherent')]

    public function Add(Request  $request , ManagerRegistry $doctrine ) : Response {
        $Adherent =  new Adherent() ;
        $form =  $this->createForm(AdherentType::class,$Adherent) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            $Adherent = $form->getData();
            $em= $doctrine->getManager() ;
            $em->persist($Adherent);
            $em->flush();
            return $this ->redirectToRoute('add_Adherent') ;
        }
        return $this->render('adherent/frontadd.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_adh', name: 'afficher_adh')]
    public function AfficheAdherent (AdherentRepository $repo   ): Response
    {
        //$repo=$this ->getDoctrine()->getRepository(Adherent::class) ;
        $Adherent=$repo->findAll() ;
        return $this->render('adherent/index.html.twig' , [
            'Adherent' => $Adherent ,
            'ajoutA' => $Adherent
        ]) ;
    }

    #[Route('/delete_adh/{id}', name: 'delete_adh')]
    public function Delete($id,AdherentRepository $repository , ManagerRegistry $doctrine) : Response {
        $Adherent=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Adherent);
        $em->flush();
        return $this->redirectToRoute("afficher_adh") ;

    }
    #[Route('/update_adh/{id}', name: 'update_adh')]
    function update(AdherentRepository $repo,$id,Request $request , ManagerRegistry $doctrine){
        $Adherent = $repo->find($id) ;
        $form=$this->createForm(AdherentType::class,$Adherent) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){

            $Adherent = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_adh') ;
        }
        return $this->render('adherent/update.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }
}
