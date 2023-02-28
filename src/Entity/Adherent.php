<?php

namespace App\Entity;

use App\Repository\AdherentRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: AdherentRepository::class)]
class Adherent
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $fullname = null;

    #[ORM\ManyToOne(inversedBy: 'adherents')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Abonnement $abonnements = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getFullname(): ?string
    {
        return $this->fullname;
    }

    public function setFullname(string $fullname): self
    {
        $this->fullname = $fullname;

        return $this;
    }

    public function getAbonnements(): ?abonnement
    {
        return $this->abonnements;
    }

    public function setAbonnements(?abonnement $abonnements): self
    {
        $this->abonnements = $abonnements;

        return $this;
    }
}
