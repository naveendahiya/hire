(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireAnswerDeleteController',CareerPortalQuestionnaireAnswerDeleteController);

    CareerPortalQuestionnaireAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalQuestionnaireAnswer'];

    function CareerPortalQuestionnaireAnswerDeleteController($uibModalInstance, entity, CareerPortalQuestionnaireAnswer) {
        var vm = this;

        vm.careerPortalQuestionnaireAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalQuestionnaireAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
